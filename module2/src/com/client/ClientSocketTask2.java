package com.client;

import com.entities.Artist;
import com.entities.Movie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientSocketTask2 {

    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 1234);
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream());
            while (sendQuery(new Scanner(System.in).nextLine(), out, in)){}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean sendQuery(String query, ObjectOutputStream out, ObjectInputStream in){
        try {
            out.writeObject(query);
            switch (query){
                case "ArtistInsert":
                case "ArtistDelete":
                    out.writeObject(Artist.read());
                    break;
                case "ArtistUpdate":
                    System.out.println("Old artist's info");
                    out.writeObject(Artist.read());
                    System.out.println("New artist's info");
                    out.writeObject(Artist.read());
                    break;
                case "ArtistGetAll":
                    try {
                        List<Artist> result = (List<Artist>) in.readObject();
                        for (Artist artist: result){
                            System.out.println(artist);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "MovieInsert":
                case "MovieUpdate":
                case "MovieDelete":
                    out.writeObject(Movie.read());
                    break;
                case "MovieGetNew":
                case "MovieGetAll":
                    try {
                        List<Movie> resultMovies = (List<Movie>) in.readObject();
                        for (Movie movie: resultMovies){
                            System.out.println(movie);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "MovieDeleteOld":
                    out.writeInt(new Scanner(System.in).nextInt());
                    break;
                case "end":
                    return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
