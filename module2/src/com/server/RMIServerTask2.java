package com.server;

import com.dao.ArtistDAO;
import com.dao.MovieDAO;
import com.entities.Artist;
import com.entities.Movie;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServerTask2 extends UnicastRemoteObject implements RMIServer {
    protected RMIServerTask2() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        RMIServer server = new RMIServerTask2();
        Registry r = LocateRegistry.createRegistry(2345);
        r.rebind("movies", server);
    }

    @Override
    public List<Artist> getArtists() throws RemoteException {
        return ArtistDAO.getAll();
    }

    @Override
    public List<Movie> getMovies() throws RemoteException {
        return MovieDAO.getAll();
    }

    @Override
    public List<Movie> getNewMovies() throws RemoteException {
        return MovieDAO.getNewMovies();
    }

    @Override
    public void addArtist(Artist artist) throws RemoteException {
        ArtistDAO.insert(artist);
    }

    @Override
    public void addMovie(Movie movie) throws RemoteException {
        MovieDAO.insert(movie);
    }

    @Override
    public void deleteMovie(Movie movie) throws RemoteException {
        MovieDAO.delete(movie);
    }

    @Override
    public void deleteOldMovies(int yearsAgo) throws RemoteException {
        MovieDAO.deleteOldMovies(yearsAgo);
    }

    @Override
    public void deleteArtist(Artist artist) throws RemoteException {
        ArtistDAO.delete(artist);
    }
}
