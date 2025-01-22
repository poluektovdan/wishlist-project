package org.example.service.command.imp;

import org.example.service.command.AbstractCommand;

public class CommandDeleteWishlist extends AbstractCommand {
    public static final CommandDeleteWishlist INSTANCE = new CommandDeleteWishlist();

    private CommandDeleteWishlist() {
        super("Удалить вишлист");
    }

    @Override
    public boolean execute() {
        getWishlistServiceDB().deleteWishlist(getUserServiceDB().getUserId());
        return true;
    }
}
