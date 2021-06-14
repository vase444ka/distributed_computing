package com.lab3;

import com.entities.City;
import com.entities.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String split = "#";

    Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public Country countryFindById(Long id) {
        var query = "CountryFindById" + split + id.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return new Country(id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public City cityFindByName(String name) {
        var query = "CityFindByName" + split + name;
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            var id = Long.parseLong(fields[0]);
            var countryId = Long.parseLong(fields[1]);
            var population = Long.parseLong(fields[3]);
            return new City(id, countryId, name, population);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Country countryFindByName(String name) {
        var query = "CountryFindByName" + split + name;
        out.println(query);
        try {
            var response = Long.parseLong(in.readLine());
            return new Country(response, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cityUpdate(City city) {
        var query = "CityUpdate" + split + city.getId() +
                split + city.getCountryId() + split + city.getName()
                + split + city.getPopulation();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean countryUpdate(Country country) {
        var query = "CountryUpdate" + split + country.getId() +
                split + country.getName();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cityInsert(City city) {
        var query = "CityInsert" +
                split + city.getCountryId() + split + city.getName()
                + split + city.getPopulation();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean countryInsert(Country country) {
        var query = "CountryInsert" +
                split + country.getName();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean countryDelete(Country country) {
        var query = "CountryDelete" + split + country.getId();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cityDelete(City city) {
        var query = "CityDelete" + split + city.getId();
        out.println(query);
        try {
            var response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Country> countryAll() {
        var query = "CountryAll";
        out.println(query);
        var list = new ArrayList<Country>();
        try {
            var response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 2) {
                var id = Long.parseLong(fields[i]);
                var name = fields[i + 1];
                list.add(new Country(id, name));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<City> cityAll() {
        var query = "CityAll";
        return getCityS(query);
    }

    public List<City> cityFindByCountryId(Long countryId) {
        var query = "CityFindByCountryId" + split + countryId.toString();
        return getCityS(query);
    }

    private List<City> getCityS(String query) {
        out.println(query);
        var list = new ArrayList<City>();
        try {
            var response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 4) {
                var id = Long.parseLong(fields[i]);
                var countryid = Long.parseLong(fields[i + 1]);
                var name = fields[i + 2];
                var population = Long.parseLong(fields[i + 3]);
                list.add(new City(id, countryid, name, population));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
