package com.dao;

import com.entities.Artist;
import com.entities.Movie;
import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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
                actors.add(ArtistDAO.getArtistById(rs.getLong("idartist")));
            }
            st.close();
            con.close();
            return actors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(@NotNull Movie movie) {
        String sql = "INSERT INTO movie(idDirector, country, title, released) VALUES (?,?,?,?)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setLong(1, ArtistDAO.getId(movie.getDirector()));
            st.setString(2, movie.getCountry());
            st.setString(3, movie.getTitle());
            st.setDate(4, (Date) movie.getReleased());
            st.executeUpdate();
            st.close();
            con.close();
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
            st.close();
            con.close();
            return parseFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Movie> getNewMovies() {
        String sql = "SELECT * FROM movie WHERE (? - EXTRACT(YEAR FROM released) <= 1)";
        try {
            Connection con = DBC.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,
                    LocalDateTime.now().getYear());
            ResultSet rs = st.executeQuery();
            st.close();
            con.close();
            return parseFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(@NotNull Movie old, @NotNull Movie updated) {
        String sql = "UPDATE movie SET country = ?, title = ?, released = ?" +
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
            //TODO directorId update
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(@NotNull Movie movie) {
        String sql = "DELETE FROM movie WHERE (country = ? AND title = ? AND released = ?)";
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
        String sql = "DELETE FROM movie WHERE (? - EXTRACT(YEAR FROM released) > ?)";
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
