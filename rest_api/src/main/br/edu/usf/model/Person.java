package br.edu.usf.model;

import br.edu.usf.utils.InputUtils;

import java.time.LocalDate;
import java.util.InputMismatchException;

public abstract class Person {

    protected String id;
    protected String name;
    protected String cpf;
    protected LocalDate dtBirth;
    protected String email;
    protected String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!InputUtils.validString(name)) {
            throw new InputMismatchException();
        }

        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDtBirth() {
        return dtBirth;
    }

    public void setDtBirth(LocalDate dtBirth) {
        this.dtBirth = dtBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!InputUtils.validString(email)) {
            throw new InputMismatchException();
        }

        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}