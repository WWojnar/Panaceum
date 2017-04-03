package com.panaceum.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection databaseConnection = null;

    public void establishConnection() {
        String database = "postgres",
                user = "postgres",
                passwd = "wodospad",
                url = "jdbc:postgresql://localhost:5432/" + database;

        try {
            Class.forName("org.postgresql.Driver");
            databaseConnection = DriverManager.getConnection(url, user, passwd);
        } catch (Exception ex) {
            System.err.println("Failed to establish connection with DB");
        }
    }

    public void closeConnection() {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }
        } catch (Exception e) {
            System.err.println("Failed to close connection with DB");
        }
    }

    public Connection getConnection() {
        return databaseConnection;
    }

}
