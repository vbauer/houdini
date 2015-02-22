package com.github.vbauer.houdini.model;

/**
 * @author Vladislav Bauer
 */

public class User {

    private int id;
    private String login;
    private String password;


    public int getId() {
        return id;
    }

    public User setId(final int id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(final String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

}
