package com.github.model;

import java.util.Collection;

public class Responsible extends LogablePerson {

    protected Collection<Student> students;

    public Collection<Student> getStudents() {
        return students;
    }
}