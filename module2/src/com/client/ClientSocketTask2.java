package com.client;

import com.entities.Artist;
import com.entities.Movie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
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
                    //TODO reading
                    out.writeObject(new Artist("Jason", "Stetham", Date.valueOf("1970-1-7")));
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
                case "ArtistUpdate":
                    //TODO reading artist
                    break;
                case "ArtistDelete":
                    //TODO reading
                    break;
                case "MovieInsert":

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
                case "MovieUpdate":
                    //TODO reading
                    break;
                case "MovieDelete":
                    //TODO reading
                    break;
                case "MovieDeleteOld":
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
