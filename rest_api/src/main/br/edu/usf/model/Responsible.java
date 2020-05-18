package br.edu.usf.model;

import java.util.ArrayList;
import java.util.Collection;

public class Responsible extends LoggablePerson {

    protected final Collection<Student> students = new ArrayList<>();

    public Collection<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }
}