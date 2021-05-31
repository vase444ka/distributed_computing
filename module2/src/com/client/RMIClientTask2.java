package com.client;

import com.entities.Artist;
import com.entities.Movie;
import com.server.RMIServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class RMIClientTask2 {
    private RMIClientTask2(){}

    public static void main(String[] args) {

        try {

            Registry registry = LocateRegistry.getRegistry(2345);
            RMIServer stub = (RMIServer) registry.lookup("movies");
            while (sendQuery(new Scanner(System.in).nextLine(), stub)){}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static boolean sendQuery(String query, RMIServer stub){
        try {
            switch (query){
                case "ArtistInsert":
                    stub.addArtist(Artist.read());
                    break;
                case "ArtistDelete":
                    stub.deleteArtist(Artist.read());
                    break;
                case "ArtistGetAll":
                    stub.getArtists().forEach(System.out::println);
                    break;
                case "MovieInsert":
                    stub.addMovie(Movie.read());
                    break;
                case "MovieDelete":
                    stub.deleteMovie(Movie.read());
                    break;
                case "MovieGetNew":
                    stub.getNewMovies().forEach(System.out::println);
                    break;
                case "MovieGetAll":
                    stub.getMovies().forEach(System.out::println);
                    break;
                case "MovieDeleteOld":
                    stub.deleteOldMovies(new Scanner(System.in).nextInt());
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
