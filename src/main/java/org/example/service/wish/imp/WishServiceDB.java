package org.example.service.wish.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.db.WishDB;
import org.example.entity.Wish;
import org.example.entity.WishPriority;
import org.example.service.wish.DBAddWishService;
import org.example.util.UtilInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishServiceDB implements DBAddWishService {
    private final WishDB wishDB = WishDB.INSTANCE;
    public static final WishServiceDB INSTANCE = new WishServiceDB();
    private String currentWishName;

    private WishServiceDB() {}

    @Override
    public String createWish(int wishlistId) {
        System.out.println("Введите название желания");
        String wishName = UtilInput.getRequiredStringFromUser();
        try {
            return wishDB.createWish(wishName, wishlistId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeWish(int wishlistId) {
        System.out.println("Введите название желания, которое хотите удалить");
        String wishName = UtilInput.getRequiredStringFromUser();
        wishDB.removeWish(wishName, wishlistId);
    }

    @Override
    public WishPriority addPriority(int wishlistId, String wishName) {
        System.out.println("Выберите приоритет");
        List<WishPriority> prioritiesList = new ArrayList<>(
                List.of(WishPriority.ONE,
                        WishPriority.TWO,
                        WishPriority.THREE,
                        WishPriority.FOUR,
                        WishPriority.FIVE)
        );
        for (int i = 0; i < prioritiesList.size(); i++) {
            WishPriority priority = prioritiesList.get(i);
            System.out.println(i+1 + ". " + priority.getPriority());
        }
        int userChoice = UtilInput.getRequiredIntFromUser(1, prioritiesList.size());
        switch (userChoice) {
            case 1:
                return wishDB.addWishPriority(wishName, wishlistId, WishPriority.ONE);
            case 2:
                return wishDB.addWishPriority(wishName, wishlistId, WishPriority.TWO);
            case 3:
                return wishDB.addWishPriority(wishName, wishlistId, WishPriority.THREE);
            case 4:
                return wishDB.addWishPriority(wishName, wishlistId, WishPriority.FOUR);
            case 5:
                return wishDB.addWishPriority(wishName, wishlistId, WishPriority.FIVE);
            default:
                break;
        }
        return null;
    }

    @Override
    public String addDescription(int wishlistId, String wishName) {
        System.out.println("Напишите описание");
        String description = UtilInput.getRequiredStringFromUser();
        return wishDB.addWishDescription(wishName, wishlistId, description);
    }

    @Override
    public String addWishLink(int wishlistId, String wishName) {
        System.out.println("Напишите ссылку");
        String link = UtilInput.getRequiredStringFromUser();
        return wishDB.addWishLink(wishName, wishlistId, link);
    }

    public Optional<Wish> findWishInWishlist(String wishlist, String wishName) throws JsonProcessingException {
        if(wishlist != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Wish> wishes = objectMapper.readValue(wishlist, new TypeReference<List<Wish>>() {});
            for (Wish wish : wishes) {
                if(wish.getWish_name().equals(wishName)) {
                    return Optional.of(wish);
                }
            }
        }
        return Optional.empty();
    }

    public String getCurrentWishName() {
        return currentWishName;
    }
}
