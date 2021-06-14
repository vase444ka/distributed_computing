package com.db;

import com.entities.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    public static City findById(long id) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            City city = null;
            if (resultSet.next()) {
                city = new City();
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setCountryId(resultSet.getLong(3));
                city.setPopulation(resultSet.getLong(4));
            }
            preparedStatement.close();
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static City findByName(String name) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            City city = null;
            if (resultSet.next()) {
                city = new City();
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setCountryId(resultSet.getLong(3));
                city.setPopulation(resultSet.getLong(4));
            }
            preparedStatement.close();
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(City city) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "UPDATE city "
                            + "SET name = ?, countryid = ?, population = ? "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setLong(2, city.getCountryId());
            preparedStatement.setLong(3, city.getPopulation());
            preparedStatement.setLong(4, city.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(City city) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "INSERT INTO city (name,countryid,population) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setLong(2, city.getCountryId());
            preparedStatement.setLong(3, city.getPopulation());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                city.setId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(City city) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "DELETE FROM city "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, city.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<City> findAll() {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM city";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<City> list = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setCountryId(resultSet.getLong(3));
                city.setPopulation(resultSet.getLong(4));
                list.add(city);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<City> findByCountryId(Long id) {
        try (Connection connection = DBC.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE countryid = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<City> list = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setCountryId(resultSet.getLong(3));
                city.setPopulation(resultSet.getLong(4));
                list.add(city);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

