package org.example.service.user.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.command.ChooseCommand;
import org.example.service.command.Command;
import org.example.service.user.StartWorkingNewUserService;

import java.util.List;

public class DefaultUserService implements StartWorkingNewUserService, ChooseCommand {
    private List<Command> commandList;

    public DefaultUserService(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void startWorkWithUser() throws JsonProcessingException {
        System.out.println("Привет, дорогой пользователь");
        boolean isWorking = chooseCommand(commandList);
        while (isWorking) {
            isWorking = chooseCommand(commandList);
        }
    }
}
