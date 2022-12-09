/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplechoiceserver;

import static Utils.Constant.PORT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    // Server Socket
    static ServerSocket server;

    // Socket for client
    static Socket socket; //at server

    // Id of client
    static int id = 1;

    // A pair that a client belong to
    static Pair pair;

    public static Vector<Worker> workers = new Vector<>();

    // Collection of all socket connected to the server
    public static HashMap<Integer, Worker> workerMap = new HashMap();

    // Collection of all pair
    public static ArrayList<Pair> pairList = new ArrayList<>();

    public static int Amount = 20;
    public static int MatchLength = 60;

    public Server(int port) {
        int numThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        ServerFunction serverF = new ServerFunction();
        //    createkey(); 
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            pairList.add(pair = new Pair());
            executor.execute(serverF);
            while (true) {
                socket = server.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                //Worker client = new Worker(socket);
                Worker client = new Worker(id, socket, in, out);
                workerMap.put(id, client);
                workers.add(client);
                executor.execute(client);
                id++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (server != null) {
                Close();
            }
        }
    }

    // join a worker to a pair when start a new  game
    public static void JoinAPair(Worker w) {
        for (Pair p : pairList) {
            if (!p.isFull()) {
                p.AddClient(w);
                if (p.isFull()) {
                    p.setIsFull(true);
                }
                w.setPair(p);
                return;
            }
        }
        pair = new Pair();
        pair.AddClient(w);
        w.setPair(pair);
        pairList.add(pair);
    }

    public void Close() {
        for (int n : workerMap.keySet()) {
            Worker w = workerMap.get(n);
            w.Close();
        }
        for (Pair p : pairList) {
            p.Stop();
        }
        try {
            System.out.println("Server Closing..");
            if (socket != null) {
                socket.close();
                System.out.println("Socket Closed");
            }
            if (server != null) {
                server.close();
                System.out.println("Server Socket Closed");
            }
            System.out.println("Server Closed");
        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }

    }

    public static void main(String[] args) throws IOException {
        Server SERVER = new Server(PORT);
    }

}
