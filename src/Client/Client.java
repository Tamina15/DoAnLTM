/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import static Utils.Constant.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    InetAddress address;
    DatagramSocket socket;
    DatagramPacket receivePacket, sendPacket;
    Scanner scanner;
    Thread s, r;
    boolean flag = true;

    Client() {
        try {
            address = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            scanner = new Scanner(System.in);
            s = new Thread(new Send());
            r = new Thread(new Receive());
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void StartThread() {
        s.start();
        r.start();
    }

//    public void SendData() {
//        byte[] data;
//        while (true) {
//            try {
//                System.out.print("User input: ");
//                String string = scanner.nextLine();
//
//                data = string.getBytes();
//                sendPacket = new DatagramPacket(data = new byte[data.length], data.length, address, PORT);
//                // Send Packet to Server
//
//                socket.send(sendPacket);
//
//                if (string.equals("bye") || socket.isConnected()) {
//                    break;
//                }
//
//                // Receive Packet From Server
//                receivePacket = new DatagramPacket(data = new byte[data.length], data.length);
//                socket.receive(receivePacket);
//                string = new String(receivePacket.getData());
//                System.out.println(string);
//            } catch (IOException ex) {
//                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    public void CloseClient() {
        System.out.println("Client Disconnect");
        scanner.close();
        socket.close();
    }

    class Send implements Runnable {

        @Override
        public void run() {
            System.out.println("Client ready to send data");
            while (flag) {
                try {
                    byte[] send;
                    System.out.print("User input: ");
                    String string = scanner.nextLine();
                    send = string.getBytes();
                    sendPacket = new DatagramPacket(send, send.length, address, PORT);
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

    class Receive implements Runnable {

        @Override
        public void run() {
            System.out.println("Client is Receiving data");
            while (flag) {
                try {
                    byte[] receive = new byte[150];
                    receivePacket = new DatagramPacket(receive, receive.length);
                    socket.receive(receivePacket);
                    String string = new String(receivePacket.getData());
                    DoSomething(string);
                } catch (IOException ex) {
                    flag = false;
                }

            }
        }

        private void DoSomething(String s) {
            // Change Data Variable
            System.out.println(s);
        }
    }

}

class main {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        //client.SendData();
        client.StartThread();
        client.r.join();
        client.s.join();
        client.CloseClient();
        System.out.println("Client complete");
    }
}
