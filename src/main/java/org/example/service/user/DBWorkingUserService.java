package org.example.service.user;

import org.example.entity.User;

import java.util.Optional;

public interface DBWorkingUserService {
    Optional<User> login();
    Optional<User> registerUser();
    boolean getUserByLogin(String login);
}
