package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.command.Command;
import org.example.service.command.imp.*;
import org.example.service.user.imp.DefaultUserService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        List<Command> commandsForLoginAndRegistration = new ArrayList<>(
                List.of(CommandLoginUser.INSTANCE,
                        CommandRegisterUser.INSTANCE,
                        CommandExit.INSTANCE));
        DefaultUserService userService = new DefaultUserService(commandsForLoginAndRegistration);
        userService.startWorkWithUser();
    }
}