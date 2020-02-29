package com.github.model;

public abstract class LogablePerson extends Person implements Logable {

    protected String login;
    protected String password;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }
}