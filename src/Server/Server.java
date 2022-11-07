/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Server;

<<<<<<< HEAD
import static src.Utils.Constant.NUMBER_AMOUNT;
=======
import static Utils.Constant.NUMBER_AMOUNT;
import static Utils.Constant.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
>>>>>>> 10f12d0a133ec9ac0b202e4eca74734cd08770d3
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran Minh Nhat
 */
public class Server {

    DatagramSocket socket;
    DatagramPacket receivePacket, sendPacket;
    byte[] buffer = new byte[1024];
    int port = 3000;
    Thread r, s;
    boolean flag = true;
    boolean doSend = false;

    public static int currentNumber;
    private static int currentIndex = 0;
    public static int[] array = new int[NUMBER_AMOUNT];

    Server() {
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("Start Server at " + socket.getLocalPort());
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Receive() {
        while (true) {
            try {
                // Receive data from client
                buffer = new byte[1024];
                receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);
                int z = 0;
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == 0) {
                        z = i;
                        break;
                    }
                }
                String string = new String(receivePacket.getData(), 0, z);
                System.out.println("Server received: " + string + " from " + receivePacket.getAddress().getHostName());
                if (string.equals("bye")) {
                    sendPacket = new DatagramPacket(new byte[0], 0, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                    break;
                }
                // Send data to client
                String result = Process(string);
                System.out.println(result);

                sendPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

    }

    public String Process(String word) throws IOException {
        String result = "Sai cu phap";
        return result;
    }

    public void CloseServer() {
        socket.close();
    }

    class Send implements Runnable {

        @Override
        public void run() {
            System.out.println("Server ready to send data");
            while (flag) {
                if (doSend) {
                    try {
                        String s = DoSomething();
                        sendPacket = new DatagramPacket(new byte[150], 150, receivePacket.getAddress(), PORT);
                        // Send Packet to Server
                        socket.send(sendPacket);
                        if (string.equals("bye") || socket.isConnected()) {
                            flag = false;
                            break;
                        }
                    } catch (IOException ex) {
                        flag = false;
                    }
                }
            }

        }

    }

    class Receive implements Runnable {

        @Override
        public void run() {
            System.out.println("Server is Receiving data");
            while (flag) {
                try {
                    byte[] receive = new byte[150];
                    receivePacket = new DatagramPacket(receive, receive.length);
                    socket.receive(receivePacket);
                    String string = new String(receivePacket.getData());
                    DoSomething(string);
                    doSend = true;
                } catch (IOException ex) {
                    flag = false;
                }

            }
        }

        private String DoSomething(String string) {
            System.out.println(string);
            return string;
        }

    }

    public static void RandomizeArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        NextNumber();
    }

    public static void NextNumber() {
        if (currentIndex + 1 <= NUMBER_AMOUNT) {
            currentNumber = array[currentIndex++];
        }
    }

}

class main {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.Receive();
        server.CloseServer();

    }
}
