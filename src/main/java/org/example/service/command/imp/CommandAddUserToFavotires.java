package org.example.service.command.imp;

import org.example.service.command.AbstractCommand;

public class CommandAddUserToFavotires extends AbstractCommand {
    public static final CommandAddUserToFavotires INSTANCE = new CommandAddUserToFavotires();

    private CommandAddUserToFavotires() {
        super("Добавить пользователя в избранное");
    }

    @Override
    public boolean execute() {
        System.out.println("Этот функционал будет добавлен позже");
        return true;
    }
}
