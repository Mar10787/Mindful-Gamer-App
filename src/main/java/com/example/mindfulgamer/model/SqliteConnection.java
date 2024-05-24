package com.example.mindfulgamer.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for creating a Sqlite database connection
 */
public class SqliteConnection {
    /**
     * Initialise instance connection
     */
    private static Connection instance = null;

    /**
     * Creates a SQliteConnection
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:users.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Checks to see if the SQlite connection is running
     * @return Connection instance of SQlite
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}