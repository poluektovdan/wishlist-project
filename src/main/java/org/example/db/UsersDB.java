package org.example.db;

import java.sql.*;

public class UsersDB {
    public static final UsersDB INSTANCE = new UsersDB();

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String ADD_USER_QUERY = "INSERT INTO users (login, password) VALUES (?, ?);";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ?;";


    private UsersDB() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Соединение с базой данных успешно установлено!");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addUser(String login, String password) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Пользователь успешно добавлен в таблицу.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public boolean findUser(String login, String password) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске пользователя: " + e.getMessage());
        }
        return false;
    }

    public boolean findUser(String login) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_QUERY)) {

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске пользователя: " + e.getMessage());
        }
        return false;
    }

    public int findUserId(String login) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_QUERY)) {

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске пользователя: " + e.getMessage());
        }

        return -1;
    }
}
