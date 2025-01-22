package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.command.AbstractCommand;

public class CommandBack extends AbstractCommand {
    public static final CommandBack INSTANCE = new CommandBack();

    private CommandBack() {
        super("Назад");
    }

    @Override
    public boolean execute() throws JsonProcessingException {
        return true;
    }
}
