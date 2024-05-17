package com.example.mindfulgamer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.sql.Date;
import java.util.*;
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
                    "gameName VARCHAR," +
                    "startGame DATETIME," +
                    "endGame DATETIME," +
                    "gamingTime VARCHAR" +
                    ")";
            statement.execute(createGameTrackingTable);

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

    public void insertSampleData() {
        try {
            // Clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM gameTracking";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO gameTracking (gameName, startGame, endGame, gamingTime) VALUES "
                    + "('Warframe', '2024-05-13 08:00:00', '2024-05-07 12:30:00', '04:30:00'),"
                    + "('Warframe', '2024-05-12 13:00:00', '2024-05-08 15:00:00', '02:00:00'),"
                    + "('Warframe', '2024-05-11 08:00:00', '2024-04-09 15:00:00', '07:00:00'),"
                    + "('Warframe', '2024-05-10 15:00:00', '2024-05-10 20:00:00', '05:00:00'),"
                    + "('Call of Duty', '2024-05-13 12:00:00', '2024-04-08 15:00:00', '03:00:00'),"
                    + "('Warzone', '2024-05-12 10:00:00', '2024-04-11 13:00:00', '03:00:00'),"
                    + "('Pokemon', '2024-05-11 15:00:00', '2024-05-11 21:00:00', '09:00:00'),"
                    + "('Overwatch', '2024-05-06 08:00:00', '2024-04-12 10:00:00', '02:00:00'),"
                    + "('Rocket League', '2024-04-11 08:30:00', '2024-04-11 09:30:00', '01:00:00'),"
                    + "('Warframe', '2024-04-07 08:00:00', '2024-04-07 12:00:00', '04:00:00'),"
                    + "('Warframe', '2024-05-08 13:00:00', '2024-05-08 15:00:00', '02:00:00'),"
                    + "('Warframe', '2024-04-09 08:00:00', '2024-04-09 15:00:00', '07:00:00'),"
                    + "('Warframe', '2024-04-10 15:00:00', '2024-04-10 20:00:00', '05:00:00'),"
                    + "('League of Legends', '2024-04-07 15:00:00', '2024-04-07 16:00:00', '01:00:00'),"
                    + "('Call of Duty', '2024-04-08 12:00:00', '2024-04-08 15:00:00', '03:00:00'),"
                    + "('Warzone', '2024-04-11 10:00:00', '2024-04-11 13:00:00', '03:00:00'),"
                    + "('Pokemon', '2024-04-11 15:00:00', '2024-04-11 21:00:00', '09:00:00'),"
                    + "('Overwatch', '2024-04-12 08:00:00', '2024-04-12 10:00:00', '02:00:00'),"
                    + "('Stardew', '2024-04-15 08:00:00', '2024-04-15 12:00:00', '04:00:00'),"
                    + "('Warframe', '2024-04-16 13:00:00', '2024-04-16 15:00:00', '02:00:00'),"
                    + "('Stardew', '2024-04-13 08:00:00', '2024-04-13 15:00:00', '07:00:00'),"
                    + "('Rocket League', '2024-04-11 08:30:00', '2024-04-11 09:30:00', '01:00:00')";
            insertStatement.execute(insertQuery);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ClearData(String table){
        /**
         * Method used to delete all data from table in the database, used only for testing
         */
        String deleteQuery = "DELETE FROM " + table;
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
            // Execute the delete query
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


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
    public void addGameTime(String gameName, String startGame, String endGame, String gamingTime){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO gameTracking (gameName, startGame, endGame, gamingTime) VALUES (?,?,?,?)");
            statement.setString(1, gameName);
            statement.setString(2, startGame);
            statement.setString(3, endGame);
            statement.setString(4,gamingTime);
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
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


    public ObservableList<String> fetchAllGameNames(){
        ObservableList<String> gameNames = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT gameName FROM gameTracking WHERE DATE(startGame) >= DATE('now', '-7 days') GROUP BY startGame";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String gameName = resultSet.getString("gameName");
                gameNames.add(gameName);
            }
        } catch (Exception e){
            e.printStackTrace();
            // Handle exception
        }
        return gameNames;
    }

    public List<String> getStartDate(String gameName){
        List<String> startDatesList = new ArrayList<>();
        try {
            // Prepare SQL statement
            String query = "SELECT startGame FROM gameTracking WHERE gameName = ? AND DATE(startGame) >= DATE('now', '-7 days') GROUP BY startGame";
            ;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,gameName);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()){
                String startDate = resultSet.getString("startGame");
                String[] Date = startDate.split(" ");
                startDatesList.add(Date[0]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return startDatesList;
    }

    public List<Integer> getGamingTimes(String gameName){
        List<Integer> gamingTimesList = new ArrayList<>();
        try {
            String query = "SELECT gamingTime FROM gameTracking WHERE gameName = ? AND DATE(startGame) >= DATE('now', '-7 days') GROUP BY startGame";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,gameName);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()){
                int gamingTime = resultSet.getInt("gamingTime");
                gamingTimesList.add(gamingTime);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return gamingTimesList;
    }




    // List of Games played in last 7 days
    public List<String> getGamesPlayedLast7Days() {
        List<String> gamesLast7Days = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT gameName, startGame FROM gameTracking WHERE DATE(startGame) BETWEEN DATE('now', '-7 days') AND DATE('now')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                gamesLast7Days.add(resultSet.getString("gameName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Set<String> uniqueGames = new HashSet<>(gamesLast7Days);
        List<String> uniqueGamesList = new ArrayList<>(uniqueGames);
        return uniqueGamesList;
    }






}