package com.example.mindfulgamer.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements IUserDAO {
    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTables();
    }

    private void createTables() {
        try {
            Statement statement = connection.createStatement();
            // Existing table for users
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "firstName VARCHAR NOT NULL," +
                    "lastName VARCHAR NOT NULL," +
                    "phone VARCHAR NOT NULL," +
                    "email VARCHAR NOT NULL," +
                    "password VARCHAR NOT NULL" +
                    ")";
            statement.execute(createUsersTable);

            // New table for GamingDetails
            String createGameTrackingTable = "CREATE TABLE IF NOT EXISTS gameTracking (" +
                    "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gameName TEXT," +
                    "startGame DATETIME," +
                    "endGame DATETIME," +
                    "gamingTime INTEGER" +
                    ")";
            statement.execute(createGameTrackingTable);

            // New table for GamingDetails
            String createStreamTrackingTable = "CREATE TABLE IF NOT EXISTS streamTracking (" +
                    "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "streamName TEXT," +
                    "startSession DATETIME," +
                    "endSession DATETIME," +
                    "streamingTime INTEGER" +
                    ")";
            statement.execute(createStreamTrackingTable);

            // New table for GamingDetails
            String createRemindersTable = "CREATE TABLE IF NOT EXISTS reminders (" +
                    "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "reminderID TEXT," +
                    "timeSpent TEXT," +
                    "timeExceeded DATETIME," +
                    "VideoGame TEXT," +
                    "notification TEXT" +
                    ")";
            statement.execute(createRemindersTable);


        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

//    private void insertSampleData() {
//        try {
//            // Clear before inserting
//            Statement clearStatement = connection.createStatement();
//            String clearQuery = "DELETE FROM users";
//            clearStatement.execute(clearQuery);
//            Statement insertStatement = connection.createStatement();
//            String insertQuery = "INSERT INTO users (firstName, lastName, phone, email) VALUES "
//                    + "('John', 'Doe', '0423423423', 'johndoe@example.com'),"
//                    + "('Jane', 'Doe', '0423423424', 'janedoe@example.com'),"
//                    + "('Jay', 'Doe', '0423423425', 'jaydoe@example.com')";
//            insertStatement.execute(insertQuery);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (firstName, lastName, phone, email, password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.executeUpdate();
            // Set the id of the new user
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();  // replace with something better later
        }
    }

    @Override
    public boolean isEmailExist(String email){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) AS count FROM users WHERE email = ?"
            );
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, phone = ?, email = ? WHERE userId = ?");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE userId = ?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(firstName, lastName, phone, email, password);
                user.setId(userId);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(firstName, lastName, phone, email, password);
                user.setId(userId);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                User user = new User(firstName, lastName, phone, email, password);
                user.setId(userId);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}