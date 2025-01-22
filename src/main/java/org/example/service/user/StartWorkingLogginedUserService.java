package org.example.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface StartWorkingLogginedUserService {
    void startWorkWithUser(String username) throws JsonProcessingException;
}
