package com.lab3;

import com.db.CityDAO;
import com.db.CountryDAO;
import com.entities.City;
import com.entities.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String split = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        String response;
        try {
            String query = in.readLine();
            if (query == null) {
                return false;
            }

            String[] fields = query.split(split);
            if (fields.length == 0) {
                return true;
            } else {
                var action = fields[0];
                Country country;
                City city;

                switch (action) {
                    case "CountryFindById":
                        var id = Long.parseLong(fields[1]);
                        country = CountryDAO.findById(id);
                        response = country.getName();
                        out.println(response);
                        break;
                    case "CityFindByCountryId":
                        id = Long.parseLong(fields[1]);
                        var list = CityDAO.findByCountryId(id);
                        var str = new StringBuilder();
                        citiesToStr(str, list);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "CityFindByName":
                        var name = fields[1];
                        city = CityDAO.findByName(name);
                        assert city != null;
                        response = city.getId() + split + city.getCountryId() + split + city.getName() + split + city.getPopulation();
                        out.println(response);
                        break;
                    case "CountryFindByName":
                        name = fields[1];
                        country = CountryDAO.findByName(name);
                        assert country != null;
                        response = country.getId() + "";
                        out.println(response);
                        break;
                    case "CityUpdate":
                        id = Long.parseLong(fields[1]);
                        var countryId = Long.parseLong(fields[2]);
                        name = fields[3];
                        var population = Long.parseLong(fields[4]);
                        city = new City(id, countryId, name, population);
                        if (CityDAO.update(city))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "CountryUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        country = new Country(id, name);
                        if (CountryDAO.update(country)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "CityInsert":
                        countryId = Long.parseLong(fields[1]);
                        name = fields[2];
                        population = Long.parseLong(fields[3]);
                        city = new City(0, countryId, name, population);
                        if (CityDAO.insert(city)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "CountryInsert":
                        name = fields[1];
                        country = new Country();
                        country.setName(name);

                        System.out.println(name);

                        if (CountryDAO.insert(country)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "CityDelete":
                        id = Long.parseLong(fields[1]);
                        city = new City();
                        city.setId(id);
                        if (CityDAO.delete(city)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "CountryDelete":
                        id = Long.parseLong(fields[1]);
                        country = new Country();
                        country.setId(id);
                        if (CountryDAO.delete(country)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "CityAll":
                        var citiesList = CityDAO.findAll();
                        str = new StringBuilder();
                        assert citiesList != null;
                        citiesToStr(str, citiesList);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "CountryAll":
                        var countriesList = CountryDAO.findAll();
                        str = new StringBuilder();
                        for (Country currCountry : countriesList) {
                            str.append(currCountry.getId());
                            str.append(split);
                            str.append(currCountry.getName());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void citiesToStr(StringBuilder str, List<City> citiesList) {
        for (City currCity : citiesList) {
            str.append(currCity.getId());
            str.append(split);
            str.append(currCity.getCountryId());
            str.append(split);
            str.append(currCity.getName());
            str.append(split);
            str.append(currCity.getPopulation());
            str.append(split);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

