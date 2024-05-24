package com.example.mindfulgamer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.*;

/**
 * Class used for any database related methods
 */
public class SqliteUserDAO implements IUserDAO {
    /**
     * Initialiase connection
     */
    private Connection connection;

    /**
     * Create a database
     */
    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTables();
    }

    /**
     * Create tables for the database that is required
     */
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

            String createRemindersTable = "CREATE TABLE IF NOT EXISTS reminders (" +
                    "reminderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "message TEXT," +
                    "Type VARCHAR," +
                    "Priority VARCHAR" +
                    ")";
            statement.execute(createRemindersTable);

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    /**
     * Gets all Reminders from database
     * @return List<String> of all reminders </String>
     */
    public List<String> getAllReminders() {
        List<String> reminders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT message FROM reminders ORDER BY reminderID DESC";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String message = resultSet.getString("message");
                reminders.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

    /**
     * Gets all Types from database reminders table
     * @return List<String> of all types from reminders </String>
     */
    public List<String> getAllTypes() {
        List<String> Types = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Type FROM reminders ORDER BY reminderID DESC";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String message = resultSet.getString("Type");
                Types.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Types;
    }

    /**
     * Gets all Priority from database reminder table
     * @return List<String> of all priority from reminders</String>
     */
    public List<String> getAllPrio() {
        List<String> Priorities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Priority FROM reminders ORDER BY reminderID DESC";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String message = resultSet.getString("Priority");
                Priorities.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Priorities;
    }

    /**
     * Inserts sample data, only used for testing
     */
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

    /**
     * Clears the Data, mainly used for testing
     * @param table the table chosen to delete all data
     */
    @Override
    public void ClearData(String table) {
        String deleteQuery = "DELETE FROM " + table;
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            // Execute the delete query
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds user to database
     * @param user The user to add.
     */
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

    /**
     * Adds Game Time to the database
     * @param gameName the name of the game
     * @param startGame the date the game session started
     * @param endGame the date the game session ended
     * @param gamingTime the total time of the session
     */
    @Override
    public void addGameTime(String gameName, String startGame, String endGame, String gamingTime) {
        // startGame and endGame must have time in order to classify as a date
        String time = "00:00:00";
        startGame = startGame + " " + time;
        endGame = endGame + " " + time;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO gameTracking (gameName, startGame, endGame, gamingTime) VALUES (?,?,?,?)");
            statement.setString(1, gameName);
            statement.setString(2, startGame);
            statement.setString(3, endGame);
            statement.setString(4, gamingTime);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds Reminders to the database
     * @param message String, the message
     * @param category String, the category
     * @param priority String, the priority
     */
    public void addReminder(String message, String category, String priority) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Reminders (message, Type, Priority) VALUES (?,?,?)");
            statement.setString(1, message);
            statement.setString(2, category);
            statement.setString(3, priority);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if email is already in database
     * @param email The email entered for the sign up
     * @return Boolean, True if in, False otherwise
     */
    @Override
    public boolean isEmailExist(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) AS count FROM users WHERE email = ?"
            );
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets user by the email
     * @param email String, email trying to find
     * @return User, the user
     */
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

    /**
     * Method used to fetch all game names in a list
     * @return List<String> List of all game names in past 7 days </String>
     */
    public ObservableList<String> fetchAllGameNames() {
        ObservableList<String> gameNames = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Distinct gameName FROM gameTracking WHERE DATE(startGame) >= DATE('now', '-7 days')";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                gameNames.add(gameName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
        return gameNames;
    }

    /**
     * Method to get the starting dates from a game
     * @param gameName String of the game name
     * @return LIst<String> The list of all game start dates from the past 7 days </String>
     */
    public List<String> getStartDate(String gameName) {
        List<String> startDatesList = new ArrayList<>();
        try {
            // Prepare SQL statement
            String query = "SELECT startGame FROM gameTracking WHERE gameName = ? AND DATE(startGame) >= DATE('now', '-7 days')";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gameName);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                String startDate = resultSet.getString("startGame");
                String[] Date = startDate.split(" ");
                startDatesList.add(Date[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDatesList;
    }

    /**
     * Method used to get the gaming times of a game from the past 7 days
     * @param gameName String of the game name
     * @return List<Integer> List of all game times <Integer\>
     */
    public List<Integer> getGamingTimes(String gameName) {
        List<Integer> gamingTimesList = new ArrayList<>();
        try {
            String query = "SELECT gamingTime FROM gameTracking WHERE gameName = ? AND DATE(startGame) >= DATE('now', '-7 days')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gameName);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                int gamingTime = resultSet.getInt("gamingTime");
                gamingTimesList.add(gamingTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gamingTimesList;
    }

    /**
     * Gets the name of the most played game of the current date
     * @return String the game name
     */
    public String getMostPlayedGame() {
        String game = "";
        try {
            String query = "SELECT gameName, SUM(gamingTime) as TotalGamingTime " +
                    "FROM gameTracking " +
                    "WHERE startGame >= datetime('now','-7 days') " +
                    "GROUP BY gameName " +
                    "ORDER BY totalGamingTime DESC " +
                    "LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement((query));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                game = resultSet.getString("gameName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    /**
     * Method used to delete specific reminders
     * @param message String of the message
     */
    public void deleteReminder(String message) {
        try {
            String deleteQuery = "DELETE FROM reminders WHERE message = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}