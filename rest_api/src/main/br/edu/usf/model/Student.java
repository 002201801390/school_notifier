package br.edu.usf.model;

import java.util.ArrayList;
import java.util.Collection;

public class Student extends LoggablePerson {

    protected final Collection<Responsible> responsible = new ArrayList<>();

    public Collection<Responsible> getResponsible() {
        return new ArrayList<>(responsible);
    }

    public void addResponsible(Responsible r) {
        responsible.add(r);
    }

    public void removeResponsible(Responsible r) {
        responsible.remove(r);
    }
}