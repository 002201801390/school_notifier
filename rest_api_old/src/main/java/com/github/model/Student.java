package com.github.model;

import java.util.Collection;

public class Student extends Person {

    protected Collection<Responsible> responsibles;

    /**
     * @return the responsibles
     */
    public Collection<Responsible> getResponsibles() {
        return responsibles;
    }
}