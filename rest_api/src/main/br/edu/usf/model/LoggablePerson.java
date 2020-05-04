package br.edu.usf.model;

public abstract class LoggablePerson extends Person implements Loggable {

    protected String login;
    protected String password;

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}