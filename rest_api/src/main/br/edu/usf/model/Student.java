package br.edu.usf.model;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Student extends LoggablePerson {

    protected final Collection<Responsible> responsible = new ArrayList<>();

    public static Student fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final Student student = new Student();
        fillPersonDefaultImpl(resultSet, student);
        return student;
    }

    public static Student fromJson(String json) {
        return new Gson().fromJson(json, Student.class);
    }

    public Collection<Responsible> getResponsible() {
        return new ArrayList<>(responsible);
    }

    public void addResponsible(Responsible r) {
        responsible.add(r);
    }

    public void removeResponsible(Responsible r) {
        responsible.remove(r);
    }

    @Override
    protected String defineRole() {
        return "student";
    }
}