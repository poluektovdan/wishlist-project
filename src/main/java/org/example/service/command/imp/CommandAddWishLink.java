package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.Wish;
import org.example.exception.NoWishesInWishlist;
import org.example.service.command.AbstractCommand;
import org.example.service.wish.ChooseWish;

import java.util.List;
import java.util.Optional;

public class CommandAddWishLink extends AbstractCommand implements ChooseWish {
    public static final CommandAddWishLink INSTANCE = new CommandAddWishLink();

    private CommandAddWishLink() { super("Добавить ссылку для желания");}

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
                String wishLink = getWishServiceDB().addWishLink(wishlistId.get(), currentWishName);
                getWishlistServiceDB().updateWishLink(wishlistId.get(), currentWishName, wishLink);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
