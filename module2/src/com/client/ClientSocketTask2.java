package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketTask2 {

    public static void main(){
        try {
            Socket socket = new Socket("localhost", 1234);
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream());
            String query = new Scanner(System.in).nextLine();
            sendQuery(query, out);
            getResponse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendQuery(String query, ObjectOutputStream out){

    }

    private static void getResponse(ObjectInputStream in){

    }
}
