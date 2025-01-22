package org.example.db;

import org.example.entity.WishPriority;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishDB {
    public static final WishDB INSTANCE = new WishDB();

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String ADD_WISH_QUERY = "INSERT INTO wishes (wish_name, wishlist_id) VALUES (?,?);";
    private static final String ADD_WISH_DESCRIPTION_QUERY = "UPDATE wishes SET wish_description = ? WHERE wish_name = ? AND wishlist_id = ?;";
    private static final String ADD_WISH_LINK_QUERY = "UPDATE wishes SET wish_link = ? WHERE wish_name = ? AND wishlist_id = ?;";
    private static final String ADD_WISH_PRIORITY_QUERY = "UPDATE wishes SET wish_priority = ? WHERE wish_name = ? AND wishlist_id = ?;";
    private static final String DELETE_WISH_QUERY = "DELETE FROM wishes WHERE wish_name = ? AND wishlist_id = ?;";

    private WishDB() {
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

    public String createWish(String wishName, int wishlistId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISH_QUERY)) {

            preparedStatement.setString(1, wishName);
            preparedStatement.setInt(2, wishlistId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Желание успешно добавлено в таблицу.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении желания");
        }
        return wishName;
    }

    public String addWishDescription(String wishName, int wishlistId, String description) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISH_DESCRIPTION_QUERY)) {

            preparedStatement.setString(1, description);
            preparedStatement.setString(2, wishName);
            preparedStatement.setInt(3, wishlistId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Описание желания успешно добавлено.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении описания желания: " + e.getMessage());
        }
        return description;
    }

    public String addWishLink(String wishName, int wishlistId, String link) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISH_LINK_QUERY)) {

            preparedStatement.setString(1, link);
            preparedStatement.setString(2, wishName);
            preparedStatement.setInt(3, wishlistId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ссылка на желание успешно добавлена.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении ссылки на желание: " + e.getMessage());
        }

        return link;
    }

    public WishPriority addWishPriority(String wishName, int wishlistId, WishPriority priority) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISH_PRIORITY_QUERY)) {

            preparedStatement.setString(1, priority.getPriority());
            preparedStatement.setString(2, wishName);
            preparedStatement.setInt(3, wishlistId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Приоритетность желания успешно добавлена.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении приоритетности желания: " + e.getMessage());
        }
        return priority;
    }

    public void removeWish(String wishName, int wishlistId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WISH_QUERY)) {

            preparedStatement.setString(1, wishName);
            preparedStatement.setInt(2, wishlistId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Желание успешно удалено.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении желания: " + e.getMessage());
        }
    }
}
