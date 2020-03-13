package com.github.model;

import java.time.LocalDate;

public abstract class Person {

    private int id;
    private String name;
    private String cpf;
    private LocalDate dtBirth;
    // TODO: List of e-mails
    private String email;
    // TODO: List of phones
    private String phone;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return the dtBirth
     */
    public LocalDate getDtBirth() {
        return dtBirth;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
}