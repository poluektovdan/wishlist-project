package org.example.entity;

public class User {
    private int id;
    private final String login;
    private final String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    //что можно делать у юзера?
    //логин, регистрация, поменять пароль, добавить другого пользователя в избранное
}
