package com.server;

import com.dao.ArtistDAO;
import com.dao.MovieDAO;
import com.entities.Artist;
import com.entities.Movie;

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
        ObjectOutputStream in = null;
        ObjectInputStream out = null;

        public Handler(Socket client) {
            this.client = client;
            try {
                out = new ObjectInputStream(client.getInputStream());
                in = new ObjectOutputStream(client.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean processQuery() throws Exception {
            String response;
            String query = (String) out.readObject();

            switch (query) {
                case "end":
                    return false;
                case "ArtistInsert":
                    Artist artist = (Artist) out.readObject();
                    ArtistDAO.insert(artist);
                    break;
                case "ArtistGetAll":
                    in.writeObject(ArtistDAO.getAll());
                    break;
                case "ArtistUpdate":
                    Artist old = (Artist) out.readObject();;
                    Artist updated = (Artist) out.readObject();;
                    ArtistDAO.update(old, updated);
                    break;
                case "ArtistDelete":
                    Artist deletedArtist = (Artist) out.readObject();;
                    ArtistDAO.delete(deletedArtist);
                    break;
                case "MovieInsert":
                    Movie movie = (Movie) out.readObject();
                    MovieDAO.insert(movie);
                    break;
                case "MovieGetAll":
                    in.writeObject(MovieDAO.getAll());
                    break;
                case "MovieGetNew":
                    in.writeObject(MovieDAO.getNewMovies());
                    break;
                case "MovieUpdate":
                    Movie oldMovie = (Movie) out.readObject();;
                    Movie updatedMovie = (Movie) out.readObject();;
                    MovieDAO.update(oldMovie, updatedMovie);
                    break;
                case "MovieDelete":
                    Movie deletedMovie = (Movie) out.readObject();;
                    MovieDAO.delete(deletedMovie);
                    break;
                case "MovieDeleteOld":
                    int N =  out.readInt();
                    MovieDAO.deleteOldMovies(N);
                    break;
            }
            return true;
        }


        public void run() {
            try {
                while(processQuery()){}
                in.close();
                out.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
