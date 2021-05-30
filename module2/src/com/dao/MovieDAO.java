package com.dao;

import com.entities.Artist;
import com.entities.Movie;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.dao.DBC.getConnection;

public class MovieDAO {
    private static List<Movie> parseFromRS(ResultSet rs) throws SQLException {
        LinkedList<Movie> movies = new LinkedList<>();
        while (rs.next()) {
            Artist director = ArtistDAO.getArtistById(rs.getLong("iddirector"));
            String title = rs.getString("title");
            String country = rs.getString("country");
            Date released = rs.getDate("released");
            Movie nextMovie = new
                    Movie(director, getActors(rs.getLong("idmovie")), country, title, released);
            movies.add(nextMovie);
        }
        return movies;
    }

    private static List<Artist> getActors(long idMovie) {
        String sql = "SELECT idactor FROM movietoactor WHERE idmovie = ?";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setLong(1, idMovie);
            ResultSet rs = st.executeQuery();
            List <Artist> actors = new LinkedList<>();
            while (rs.next()){
                actors.add(ArtistDAO.getArtistById(rs.getLong("idactor")));
            }
            st.close();
            con.close();
            return actors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getId(Movie movie){
        String sql = "SELECT idmovie FROM public.movie WHERE (country = ? AND title = ? AND released = ?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, movie.getCountry());
            st.setString(2, movie.getTitle());
            st.setDate(3, (Date) movie.getReleased());
            ResultSet rs = st.executeQuery();

            long idmovie = -1;
            if (rs.next()){
                idmovie = rs.getLong("idmovie");
            }
            st.close();
            con.close();
            return idmovie;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    private static void insertActors(Movie movie){
        long idmovie = getId(movie);
        try{
            for (Artist artist: movie.getActors()){
                String sql = "INSERT INTO movietoactor (idmovie, idactor) VALUES (?, ?)";
                Connection con = DBC.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setLong(1, idmovie);
                pst.setLong(2, ArtistDAO.getId(artist));
                pst.executeUpdate();
                pst.close();
                con.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean insert(Movie movie) {
        String sql = "INSERT INTO movie(idDirector, country, title, released) VALUES (?,?,?,?)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            long idDirector = ArtistDAO.getId(movie.getDirector());
            if (idDirector == -1){
                ArtistDAO.insert(movie.getDirector());
                idDirector =  ArtistDAO.getId(movie.getDirector());
            }
            st.setLong(1, idDirector);
            st.setString(2, movie.getCountry());
            st.setString(3, movie.getTitle());
            st.setDate(4, (Date) movie.getReleased());
            st.executeUpdate();
            st.close();
            con.close();
            insertActors(movie);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Movie> getAll() {
        String sql = "SELECT * FROM movie";
        try {
            Connection con = DBC.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Movie> res = parseFromRS(rs);
            st.close();
            con.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Movie> getNewMovies() {
        String sql = "SELECT * FROM public.movie WHERE (? - EXTRACT(YEAR FROM released) <= 1)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,
                    LocalDateTime.now().getYear());
            ResultSet rs = st.executeQuery();
            var res = parseFromRS(rs);
            st.close();
            con.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(Movie old, Movie updated) {
        String sql = "UPDATE public.movie SET country = ?, title = ?, released = ?" +
                "WHERE (country = ? AND title = ? AND released = ?)";
        try {
            Connection con = DBC.getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, updated.getCountry());
            st.setString(2, updated.getTitle());
            st.setDate(3, (Date) updated.getReleased());
            st.setString(4, old.getCountry());
            st.setString(5, old.getTitle());
            st.setDate(6, (Date) old.getReleased());
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(Movie movie) {
        String sql = "DELETE FROM public.movie WHERE (country = ? AND title = ? AND released = ?)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, movie.getCountry());
            st.setString(2, movie.getTitle());
            st.setDate(3, (Date) movie.getReleased());
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteOldMovies(int yearsAgo) {
        String sql = "DELETE FROM public.movie WHERE (? - EXTRACT(YEAR FROM released) > ?)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, LocalDateTime.now().getYear());
            st.setInt(2, yearsAgo);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
