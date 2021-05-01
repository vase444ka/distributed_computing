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
        String sql = "SELECT * FROM public.artist WHERE idartist = ?";
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

    public static boolean insert(@NotNull Artist artist){
        String sql = "INSERT INTO public.artist(firstname, lastname, dateofbirth) VALUES (?,?,?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, artist.getFirstName());
            st.setString(2, artist.getLastName());
            st.setDate(3, (Date) artist.getDateOfBirth());
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
        String sql = "SELECT * FROM public.artist";
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
        String sql = "SELECT idartist FROM public.artist WHERE (firstname = ? AND lastname = ? AND dateofbirth = ?)";
        try{
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, artist.getFirstName());
            st.setString(2, artist.getLastName());
            st.setDate(3, (Date) artist.getDateOfBirth());
            ResultSet rs = st.executeQuery();

            long idartist = -1;
            if (rs.next()){
                idartist = rs.getLong("idartist");
                st.close();
                con.close();
                return idartist;
            }
            else{
                st.close();
                con.close();
                insert(artist);
                return getId(artist);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean update(Artist old, Artist updated){
        String sql = "UPDATE public.artist SET firstname = ?, lastname = ?, dateofbirth = ?" +
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
        String sql = "DELETE FROM public.artist WHERE (firstname = ? AND lastname = ? AND dateofbirth = ?)";
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
