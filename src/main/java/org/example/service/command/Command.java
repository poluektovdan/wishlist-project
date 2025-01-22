package org.example.service.command;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Command {
    boolean execute() throws JsonProcessingException;
    String getCommandName();
}
