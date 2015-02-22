package com.github.vbauer.houdini.model;

/**
 * @author Vladislav Bauer
 */

public class UserDTO {

    private int id;
    private String login;
    private String password;


    public int getId() {
        return id;
    }

    public UserDTO setId(final int id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserDTO setLogin(final String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(final String password) {
        this.password = password;
        return this;
    }

}
