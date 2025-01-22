package org.example.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Wish;
import org.example.entity.Wishlist;
import org.example.exception.NoWishesInWishlist;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishlistsDB {
    public static final WishlistsDB INSTANCE = new WishlistsDB();

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String ADD_WISHLIST_QUERY = "INSERT INTO wishlists (wishlist_name, user_id) VALUES (?,?);";
    private static final String REMOVE_WISHLIST_QUERY = "DELETE FROM wishlists WHERE user_id = ? AND wishlist_name = ?;";
    private static final String SHOW_WISHLISTS_QUERY = "SELECT * FROM wishlists WHERE user_id = ?;";
    private static final String FIND_WISHLIST_QUERY = "SELECT COUNT(*) FROM wishlists WHERE user_id = ? AND wishlist_name = ?;";
    private static final String FIND_WISHLIST_ID_QUERY = "SELECT wishlists.wishlist_id FROM wishlists WHERE user_id = ? AND wishlist_name = ?;";
    private static final String ADD_WISH_TO_WISHLIST_QUERY = "UPDATE wishlists SET wishlist = ? WHERE wishlist_id = ?;";
    private static final String GET_WISHLIST_QUERY = "SELECT wishlist FROM wishlists WHERE wishlist_id = ? AND wishlist IS NOT NULL;";

    private WishlistsDB() {
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

    public void addWishlist(String wishlistName, int userID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISHLIST_QUERY)) {

            preparedStatement.setString(1, wishlistName);
            preparedStatement.setInt(2, userID);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Вишлист успешно добавлен в таблицу.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении вишлиста: " + e.getMessage());
        }
    }

    public void removeWishlist(String wishlistName, int userID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_WISHLIST_QUERY)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, wishlistName);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Вишлист успешно удален из таблицы.");
            } else {
                System.out.println("Что-то пошло не по плану");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении вишлиста: " + e.getMessage());
        }
    }

    public String getWishlists(int userID) {
        String wishlists = "Ваши вишлисты: \n";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHOW_WISHLISTS_QUERY)) {

            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                wishlists += "Вишлист " + resultSet.getString("wishlist_name")
                        + " \n" + resultSet.getString("wishlist") + " \n";
            }
        } catch (SQLException e) {
            System.err.println("Ошибка вывода вишлиста: " + e.getMessage());
        }

        return wishlists;
    }

    public List<String> getWishlistsList(int userID) {
        List<String> wishlistsList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHOW_WISHLISTS_QUERY)) {

            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                wishlistsList.add(resultSet.getString("wishlist_name"));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка вывода вишлиста: " + e.getMessage());
        }

        return wishlistsList;
    }

    public boolean findWishlist(String wishlistName, int userID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_WISHLIST_QUERY)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, wishlistName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении вишлиста: " + e.getMessage());
        }

        return false;
    }

    public Optional<Integer> findWishlistId(String wishlistName, int userID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_WISHLIST_ID_QUERY)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, wishlistName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске вишлиста: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void updateWishesInWishlist(List<Wish> wishes, int wishlistID) {
        //при создании желания, оно должно сразу добавляться в вишлист
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_WISH_TO_WISHLIST_QUERY)) {

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(wishes);
            preparedStatement.setString(1, json);
            preparedStatement.setInt(2, wishlistID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении виша в вишлист: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWishlist(int wishlistID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_WISHLIST_QUERY)) {

            preparedStatement.setInt(1, wishlistID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("wishlist");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске вишлиста: " + e.getMessage());
        }
        return "";
    }

    public Optional<List<Wish>> getWishlistAsList(int wishlistID) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_WISHLIST_QUERY)) {

            preparedStatement.setInt(1, wishlistID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String wishlists = resultSet.getString("wishlist");

                ObjectMapper objectMapper = new ObjectMapper();
                return Optional.of(objectMapper.readValue(wishlists, new TypeReference<List<Wish>>() {
                }));
            } else {
                throw new NoWishesInWishlist("В вашем вишлисте нет желаний, добавьте хотя бы одно");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске вишлиста: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
