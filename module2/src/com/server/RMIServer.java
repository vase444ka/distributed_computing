package com.server;

import com.entities.Artist;
import com.entities.Movie;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RMIServer extends Remote {
    List<Artist> getArtists() throws RemoteException;
    List<Movie> getMovies() throws RemoteException;
    List<Movie> getNewMovies() throws RemoteException;

    void addArtist(Artist actor) throws RemoteException;
    void addMovie(Movie movie) throws RemoteException;

    void deleteMovie(Movie movie)throws RemoteException;
    void deleteOldMovies(int yearsAgo) throws RemoteException;
    void deleteArtist(Artist artist) throws RemoteException;
}
