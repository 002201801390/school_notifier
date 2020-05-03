package br.edu.usf.model;

import br.edu.usf.utils.InputUtils;

import java.util.InputMismatchException;

public abstract class LoggablePerson extends Person implements Loggable {

    protected String login;
    protected String password;

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (!InputUtils.validString(login)) {
            throw new InputMismatchException();
        }

        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!InputUtils.validString(password)) {
            throw new InputMismatchException();
        }

        this.password = password;
    }
}