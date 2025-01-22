package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.Wish;
import org.example.entity.WishPriority;
import org.example.exception.NoWishesInWishlist;
import org.example.service.command.AbstractCommand;
import org.example.service.wish.ChooseWish;

import java.util.List;
import java.util.Optional;

public class CommandAddWishPriority extends AbstractCommand implements ChooseWish {
    public static final CommandAddWishPriority INSTANCE = new CommandAddWishPriority();

    private CommandAddWishPriority() {
        super("Добавить приоритет желанию");
    }

    @Override
    public boolean execute() throws JsonProcessingException {
        try {
            Optional<Integer> wishlistId = getWishlistServiceDB().findWishlistId(getUserServiceDB().getUserId());
            if (wishlistId.isPresent()) {
                Optional<List<Wish>> wishes = getWishlistServiceDB().getWishlistAsList(wishlistId.get());
                if(wishes.isEmpty()) {
                    throw new NoWishesInWishlist("В вашем вишлисте нет желаний, добавьте хотя бы одно");
                }
                String currentWishName = getWishes(getWishlistServiceDB(), wishlistId.get());
                WishPriority priority = getWishServiceDB().addPriority(wishlistId.get(), currentWishName);
                getWishlistServiceDB().updatePriorityOfWish(wishlistId.get(), currentWishName, priority);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
