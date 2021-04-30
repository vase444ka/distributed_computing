package com.dao;

import com.entities.Artist;
import com.entities.Movie;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static com.dao.DBC.getConnection;

public class ArtistDAO {
    private static List<Artist> parseFromRS(ResultSet rs) throws SQLException{
        LinkedList<Artist> artists = new LinkedList<>();
        while (rs.next()){
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            Date dateOfBirth = rs.getDate("dateofbirth");
            Artist nextArtist = new Artist(firstName, lastName, dateOfBirth);
            artists.add(nextArtist);
        }
        return artists;
    }

    public static Artist getArtistById(long id){
        String sql = "SELECT * FROM artist WHERE idartist = ?";
        try{
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            st.close();
            con.close();
            return parseFromRS(rs).get(0);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insert(@NotNull Movie movie){
        String sql = "INSERT INTO movie(idDirector, country, title, released) VALUES (?,?,?,?)";
        try{
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            //TODO director id
            st.setString(2, movie.getCountry());
            st.setString(3, movie.getTitle());
            st.setDate(4, (Date) movie.getReleased());
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Artist> getAll(){
        String sql = "SELECT * FROM artist";
        try{
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            st.close();
            con.close();
            return parseFromRS(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static long getId(Artist artist){
        String sql = "SELECT idartist FROM artist WHERE (firstname = ? AND lastname = ? AND dateofbirth = ?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, artist.getFirstName());
            st.setString(2, artist.getLastName());
            st.setDate(3, (Date) artist.getDateOfBirth());
            ResultSet rs = st.executeQuery();

            st.close();
            con.close();
            return rs.getLong("idartist");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean update(Artist old, Artist updated){
        String sql = "UPDATE artist SET firstname = ?, lastname = ?, dateofbirth = ?" +
                "WHERE (firstname = ? AND lastname = ? AND dateofbirth = ?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, updated.getFirstName());
            st.setString(2, updated.getLastName());
            st.setDate(3, (Date) updated.getDateOfBirth());
            st.setString(4, old.getFirstName());
            st.setString(5, old.getLastName());
            st.setDate(6, (Date) old.getDateOfBirth());
            st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(Artist artist){
        String sql = "DELETE FROM artist WHERE (firstname = ? AND lastname = ? AND dateofbirth = ?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, artist.getFirstName());
            st.setString(2, artist.getLastName());
            st.setDate(3, (Date) artist.getDateOfBirth());
            st.executeUpdate();

            st.close();
            con.close();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}