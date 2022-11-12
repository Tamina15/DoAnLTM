/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran Minh Nhat
 */
public class Server1 {

    static ServerSocket server;
    static Socket socket; //at server
    static int id = 1;

    static public HashMap<Integer, ServerThread> socketMap = new HashMap();

    public static void main(String args[]) {

        System.out.println("Server Listening......");
        try {
            server = new ServerSocket(1235);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server Error");
        }

        while (true) {
            try {
                socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("connection Established with" + socket.getInetAddress() + " at: " + socket.getLocalPort());
                ServerThread st = new ServerThread(id, socket, in, out);
                Thread t = new Thread(st);
                socketMap.put(id, st);
                id++;
                t.start();
            } catch (IOException e) {
                System.out.println("Connection Error");
            }
        }
    }

    public static HashMap<Integer, ServerThread> getSocketMap() {
        return socketMap;
    }

}
