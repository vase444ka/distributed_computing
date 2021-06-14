package com.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBC {
    private static String config = "db.conf";
    private static Connection connection = null;
    private final static String url;
    private final static String user;
    private final static String password;
    private final static String JDBCDriverName;

    static {
        Properties connInfo = new Properties();
        try {
            FileInputStream in = new FileInputStream(config);
            connInfo.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        url = connInfo.getProperty("connection.url");
        user = connInfo.getProperty("user.name");
        password = connInfo.getProperty("password");
        JDBCDriverName = connInfo.getProperty("driver");
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(JDBCDriverName);
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
