package com.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBC {
    static String config = "db.conf";

    public static Connection getConnection() throws SQLException {
        Properties connInfo = new Properties();
        try {
            FileInputStream in = new FileInputStream(config);
            connInfo.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String connectionURL = connInfo.getProperty("connection.url");
        String username = connInfo.getProperty("user.name");
        String password = connInfo.getProperty("password");

        try {
            Class.forName(connInfo.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  DriverManager.getConnection(connectionURL, username, password);
    }
}
