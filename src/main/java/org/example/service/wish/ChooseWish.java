package org.example.service.wish;

import org.example.entity.Wish;
import org.example.util.UtilInput;
import org.example.wishlist.imp.WishlistServiceDB;

import java.util.List;
import java.util.Optional;

public interface ChooseWish {
    default String getWishes(WishlistServiceDB wishlistServiceDB, int wishlistId) {
        String currentWishName = null;
        System.out.println("Выбирете желание, которому хотите добавить описание");
        Optional<List<Wish>> wishes = wishlistServiceDB.getWishlistAsList(wishlistId);
        if (wishes.isPresent()) {
            for(int i=0; i<wishes.get().size(); i++) {
                System.out.println(i + ". " + wishes.get().get(i));
            }
            int userChoice = UtilInput.getRequiredIntFromUser(0,wishes.get().size());
            for(int i=0; i<wishes.get().size(); i++) {
                Wish wish = wishes.get().get(i);
                if(wishes.get().get(userChoice).equals(wish)) {
                    currentWishName = wish.getWish_name();
                }
            }
        }
        return currentWishName;
    }
}
