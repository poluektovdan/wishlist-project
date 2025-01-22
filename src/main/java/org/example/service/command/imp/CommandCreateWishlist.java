package org.example.service.command.imp;

import org.example.service.command.AbstractCommand;

public class CommandCreateWishlist extends AbstractCommand {
    public static final CommandCreateWishlist INSTANCE = new CommandCreateWishlist();

    private CommandCreateWishlist() {
        super("Создать вишлист");
    }

    @Override
    public boolean execute() {
        getWishlistServiceDB().createWishlist(getUserServiceDB().getUserId());
        return true;
    }
}
