/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Utils.Constant.ADDRESS;
import static Utils.Constant.PORT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran Minh Nhat
 */
public class Client1 {

        private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static Scanner scanner = null;
    private boolean flag = true;
    Send s;
    Receive r;

    public Client1() {
        try {
            socket = new Socket("localhost", 1235);
            System.out.println("Connected");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error. Cannot Connect");
        }

    }

    public void StartThread() {
        s = new Send();
        r = new Receive();
        s.start();
        r.start();
    }

    class Send extends Thread {

        @Override
        public void run() {
            try {
                while (flag) {
                    System.out.print("Client input: ");
                    String line = scanner.nextLine();
                    out.write(line);
                    out.newLine();
                    out.flush();
                    if (line.equals("bye")) {
                        flag = false;
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                flag = false;
            } finally {
                CloseClient();
            }
        }
    }

    class Receive extends Thread {

        @Override
        public void run() {
            try {
                while (flag) {
                    String line = in.readLine();
                    System.out.println(line);
                    if (line.equals("bye")) {
                        flag = false;
                        return;
                    }
                }
            } catch (IOException ex) {
                CloseClient();
                flag = false;
            } finally {
                CloseClient();
            }
        }
    }

    public void CloseClient() {
        try {
            System.out.println("Connection Closing..");
            if (scanner != null) {
                scanner.close();
                System.out.println(" Scanner Closed");
            }
            if (in != null) {
                in.close();
                System.out.println(" Socket Input Stream Closed");
            }

            if (out != null) {
                out.close();
                System.out.println("Socket Out Closed");
            }
            if (socket != null) {
                socket.close();
                System.out.println("Socket Closed");
            }

        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.StartThread();
        try {
            client.r.join();
            client.s.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.CloseClient();

    }

}
