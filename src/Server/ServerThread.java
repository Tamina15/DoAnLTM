/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Tran Minh Nhat
 */
public class ServerThread implements Runnable {

    int id;
    String line = null;
    BufferedReader in = null;
    BufferedWriter out = null;
    Socket s = null;
    ServerThread opponent;
    String name;

    public ServerThread(int id, Socket s, BufferedReader in, BufferedWriter out) {
        this.id = id;
        this.s = s;
        name = id + " : " + s.getInetAddress() + " : ";
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        System.out.println("Thread " + id + " run");
        try {
            while (true) {
                System.out.println(id + " Reading");
                line = in.readLine();
                System.out.println(line);
                if (line.equals("bye")) {
                    break;
                }
                Process(line);
                System.out.println(name + " Response to Client " + s.getInetAddress() + " :  " + line);
            }
        } catch (IOException e) {
            System.out.println(name + "IO Error/ Client " + id + " terminated abruptly");
        } catch (NullPointerException e) {
            System.out.println(name + "Client" + id + " Closed");
        } finally {
            Close();
        }//end finally

    }

    private void Process(String line) throws IOException {
        System.out.println(name + " processing line");

        StringTokenizer st = new StringTokenizer(line, "#");
        String command = st.nextToken();
        String chat = st.nextToken();

        if (command.equals("all")) {
            for (Integer i : Server1.getSocketMap().keySet()) {
                if (i != id) {
                    Server1.getSocketMap().get(i).out.write(chat);
                    Server1.getSocketMap().get(i).out.newLine();
                    Server1.getSocketMap().get(i).out.flush();

                }
            }
        } else {
            int i = Integer.parseInt(command);
            if (Server1.getSocketMap().containsKey(i)) {
                Server1.getSocketMap().get(i).out.write(chat);
                Server1.getSocketMap().get(i).out.newLine();
                Server1.getSocketMap().get(i).out.flush();
            }
        }

    }

    private void Close() {
        Server1.getSocketMap().remove(id);
        try {
            System.out.println(name + "Connection Closing..");
            if (in != null) {
                in.close();
                System.out.println(name + " Socket Input Stream Closed");
            }

            if (out != null) {
                out.close();
                System.out.println(name + "Socket Out Closed");
            }
            if (s != null) {
                s.close();
                System.out.println(name + "Socket Closed");
            }

        } catch (IOException ie) {
            System.out.println(name + "Socket Close Error");
        }
    }

}
