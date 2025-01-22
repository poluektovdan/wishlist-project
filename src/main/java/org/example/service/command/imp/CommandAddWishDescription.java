package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.Wish;
import org.example.exception.NoWishesInWishlist;
import org.example.service.command.AbstractCommand;
import org.example.service.wish.ChooseWish;
import org.example.util.UtilInput;

import java.util.List;
import java.util.Optional;

public class CommandAddWishDescription extends AbstractCommand implements ChooseWish {
    public static final CommandAddWishDescription INSTANCE = new CommandAddWishDescription();

    private CommandAddWishDescription() {
        super("Добавить описание желанию");
    }


    @Override
    public boolean execute() throws JsonProcessingException {
        try {
            Optional<Integer> wishlistId = getWishlistServiceDB().findWishlistId(getUserServiceDB().getUserId());
            if(wishlistId.isPresent()) {
                Optional<List<Wish>> wishes = getWishlistServiceDB().getWishlistAsList(wishlistId.get());
                if(wishes.isEmpty()) {
                    throw new NoWishesInWishlist("В вашем вишлисте нет желаний, добавьте хотя бы одно");
                }
                String currentWishName = getWishes(getWishlistServiceDB(), wishlistId.get());
                String wishDescription = getWishServiceDB().addDescription(wishlistId.get(), currentWishName);
                getWishlistServiceDB().updateDescriptionOfWish(wishlistId.get(), wishDescription, currentWishName);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
