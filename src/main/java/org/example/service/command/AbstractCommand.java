package org.example.service.command;

import org.example.service.user.imp.UserServiceDB;
import org.example.service.wish.imp.WishServiceDB;
import org.example.wishlist.imp.WishlistServiceDB;

public abstract class AbstractCommand implements Command {
    private String title;
    private UserServiceDB userServiceDB;
    private WishlistServiceDB wishlistServiceDB;
    private WishServiceDB wishServiceDB;

    protected AbstractCommand(String title) {
        this.title = title;
        this.userServiceDB = UserServiceDB.INSTANCE;
        this.wishlistServiceDB = WishlistServiceDB.INSTANCE;
        this.wishServiceDB = WishServiceDB.INSTANCE;
    }

    protected UserServiceDB getUserServiceDB() {
        return userServiceDB;
    }

    public WishlistServiceDB getWishlistServiceDB() {
        return wishlistServiceDB;
    }

    public WishServiceDB getWishServiceDB() {
        return wishServiceDB;
    }

    @Override
    public String getCommandName() {
        return title;
    }

    @Override
    public String toString() {
        return "AbstractCommand{" +
                "title='" + title + '\'' +
                '}';
    }
}
