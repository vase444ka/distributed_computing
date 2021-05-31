package com.server;

import com.entities.Artist;
import com.entities.Movie;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServer extends Remote {
    ArrayList<Artist> getArtists() throws RemoteException;
    ArrayList<Movie> getMovies() throws RemoteException;
    ArrayList<Movie> getNewMovies() throws RemoteException;

    void addArtist(Artist actor) throws RemoteException;
    void addMovie(Movie movie) throws RemoteException;

    void deleteMovie(Movie movie)throws RemoteException;
    void deleteOldMovies(int yearsAgo) throws RemoteException;
    void deleteArtist(Artist artist) throws RemoteException;

    ArrayList<Artist> getNFilmsActors(int films) throws RemoteException;

    ArrayList<Movie> getRecentFilms() throws RemoteException;

    void addRole(Long filmId, Long actorId) throws RemoteException;

    void deleteRole(Long filmId, Long actorId) throws RemoteException;

    void deleteNYearFilm(int years) throws RemoteException;
}
