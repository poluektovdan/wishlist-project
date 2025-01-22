package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.command.AbstractCommand;
import org.example.service.command.ChooseCommand;
import org.example.service.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandEditWish extends AbstractCommand implements ChooseCommand {
    public static final CommandEditWish INSTANCE = new CommandEditWish();
    private List<Command> commands = new ArrayList<>(List.of(
            CommandAddWishDescription.INSTANCE,
            CommandAddWishPriority.INSTANCE,
            CommandAddWishLink.INSTANCE,
            CommandBack.INSTANCE
    ));

    private CommandEditWish() {
        super("Редактировать желание");
    }

    @Override
    public boolean execute() throws JsonProcessingException {
        return chooseCommand(commands);
    }
}
