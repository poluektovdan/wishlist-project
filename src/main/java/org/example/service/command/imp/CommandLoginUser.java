package org.example.service.command.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.User;
import org.example.service.command.AbstractCommand;
import org.example.service.command.Command;
import org.example.service.user.StartWorkingLogginedUserService;
import org.example.util.UtilInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandLoginUser extends AbstractCommand implements StartWorkingLogginedUserService {
    public static final CommandLoginUser INSTANCE = new CommandLoginUser();

    private final List<Command> commandList = new ArrayList<>(
            List.of(CommandCreateWishlist.INSTANCE,
                    CommandCreateWish.INSTANCE,
                    CommandEditWish.INSTANCE,
                    CommandShowWishlists.INSTANCE,
                    CommandDeleteWishlist.INSTANCE,
                    CommandAddUserToFavotires.INSTANCE,
                    CommandExit.INSTANCE));

    private CommandLoginUser() {
        super("Логин");
    }

    @Override
    public boolean execute() {
        try {
            Optional<User> user = getUserServiceDB().login();
            if (user.isPresent()) {
                getUserServiceDB().setUserId(user.get().getId());
                startWorkWithUser(user.get().getLogin());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public void startWorkWithUser(String login) throws JsonProcessingException {
        System.out.println("Привет, " + login);
        boolean isWorking = chooseCommand();
        while (isWorking) {
            isWorking = chooseCommand();
        }
    }

    private boolean chooseCommand() throws JsonProcessingException {
        System.out.println("Выбери команду, которую хочешь выполнить");
        for (int i = 1; i <= commandList.size(); i++) {
            System.out.printf("%s - %s%n", i, commandList.get(i - 1).getCommandName());
        }
        int numberChoose = UtilInput.getRequiredIntFromUser(1, commandList.size());
        return commandList.get(numberChoose - 1).execute();
    }
}
