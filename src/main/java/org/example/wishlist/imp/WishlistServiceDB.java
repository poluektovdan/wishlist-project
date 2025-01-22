package org.example.wishlist.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.db.WishlistsDB;
import org.example.entity.Wish;
import org.example.entity.WishPriority;
import org.example.entity.Wishlist;
import org.example.exception.NoWishlistException;
import org.example.exception.WishAlreadyExistsException;
import org.example.exception.WishlistNotFoundException;
import org.example.util.UtilInput;
import org.example.wishlist.DBWishlistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishlistServiceDB implements DBWishlistService {
    private final WishlistsDB wishlistsDB = WishlistsDB.INSTANCE;
    public static final WishlistServiceDB INSTANCE = new WishlistServiceDB();

    private WishlistServiceDB() {}

    @Override
    public void createWishlist(int userId) {
        System.out.println("Введите название вишлиста");
        String wishlistName = UtilInput.getRequiredStringFromUser();
        wishlistsDB.addWishlist(wishlistName, userId);
    }

    @Override
    public void deleteWishlist(int userId) {
        System.out.println("Введите название вишлиста, который хотите удалить");
        String wishlistName = UtilInput.getRequiredStringFromUser();
        if(findWishlist(wishlistName, userId)) {
            wishlistsDB.removeWishlist(wishlistName, userId);
        } else {
            System.out.println("Вишлиста с таким названием не найдено");
        }
    }

    @Override
    public void getWishlists(int userId) {
        System.out.println(wishlistsDB.getWishlists(userId));
    }

    @Override
    public Optional<Integer> findWishlistId(int userId) {
        if(wishlistsDB.getWishlistsList(userId).isEmpty()) {
            throw new NoWishlistException("У вас нет ни одного вишлиста, создайте хотя бы 1");
        }
        String wishlistName = null;
        System.out.println("Выберите вишлист");
        List<String> wishlistsList = wishlistsDB.getWishlistsList(userId);
        for (int i = 0; i < wishlistsList.size(); i++) {
            System.out.println(i + ". " + wishlistsList.get(i));
        }
        int userChoice = UtilInput.getRequiredIntFromUser(0,wishlistsList.size());

        for (String string : wishlistsList) {
            if (string.equals(wishlistsList.get(userChoice))) {
                wishlistName = string;
            }
        }

        if(findWishlist(wishlistName, userId)) {
            return wishlistsDB.findWishlistId(wishlistName, userId);
        } else {
            throw new WishlistNotFoundException("Такого вишлиста не существует");
        }
    }

    @Override
    public void updateWishesInWishlist(int wishlistID, String wishName) throws JsonProcessingException {
        String wishlist = getWishlist(wishlistID);
        if(wishlist != null && wishlist != "") {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Wish> wishes = objectMapper.readValue(wishlist, new TypeReference<List<Wish>>() {});
            Wish wish = new Wish(wishName);
            if(!wishes.contains(wish)) {
                wishes.add(wish);
                wishlistsDB.updateWishesInWishlist(wishes, wishlistID);
            } else {
                throw new WishAlreadyExistsException("Такое желание уже существует");
            }
        } else {
            wishlistsDB.updateWishesInWishlist(initializeNewWishlist(wishName), wishlistID);
        }
    }

    @Override
    public String getWishlist(int wishlistID) {
        return wishlistsDB.getWishlist(wishlistID);
    }

    public boolean findWishlist(String wishlistName, int userId) {
        return wishlistsDB.findWishlist(wishlistName, userId);
    }

    private List<Wish> initializeNewWishlist(String wishName) {
        List<Wish> wishes = new ArrayList<>();
        wishes.add(new Wish(wishName));
        return wishes;
    }

    public void updateDescriptionOfWish(int wishlistID, String description, String wishName) throws JsonProcessingException {
        String wishlist = getWishlist(wishlistID);
        if(wishlist != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Wish> wishes = objectMapper.readValue(wishlist, new TypeReference<List<Wish>>() {});
            for(Wish wish : wishes) {
                if(wish.getWish_name().equals(wishName)) {
                    wish.setWish_description(description);
                }
            }
            wishlistsDB.updateWishesInWishlist(wishes, wishlistID);
        }
    }

    public void updatePriorityOfWish(int wishlistID, String wishName, WishPriority priority) throws JsonProcessingException {
        String wishlist = getWishlist(wishlistID);
        if(wishlist != null && wishlist != "") {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Wish> wishes = objectMapper.readValue(wishlist, new TypeReference<List<Wish>>() {});
            for(Wish wish : wishes) {
                if(wish.getWish_name().equals(wishName)) {
                    wish.setPriority(priority);
                }
            }
            wishlistsDB.updateWishesInWishlist(wishes, wishlistID);
        }
    }

    public void updateWishLink(int wishlistID, String wishName, String wishLink) throws JsonProcessingException {
        String wishlist = getWishlist(wishlistID);
        if(wishlist != null && wishlist != "") {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Wish> wishes = objectMapper.readValue(wishlist, new TypeReference<List<Wish>>() {});
            for(Wish wish : wishes) {
                if(wish.getWish_name().equals(wishName)) {
                    wish.setWish_link(wishLink);
                }
            }
            wishlistsDB.updateWishesInWishlist(wishes, wishlistID);
        }
    }

    public Optional<List<Wish>> getWishlistAsList(int wishlistID) {
        return wishlistsDB.getWishlistAsList(wishlistID);
    }
}
