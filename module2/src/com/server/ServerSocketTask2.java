package com.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTask2 {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                new Thread(new Handler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Handler implements Runnable {
        Socket client;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        public Handler(Socket client) {
            this.client = client;
            try {
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void processQuery() throws Exception {
            String response;
            String query = (String) in.readObject();

            switch (query) {
                case "ActorInsert":
                    //DAO query
                    out.write(1);
                    break;
            }

        }


        public void run() {
            try {
                processQuery();
                in.close();
                out.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
