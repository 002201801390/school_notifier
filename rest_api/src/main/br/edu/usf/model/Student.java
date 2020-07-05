package br.edu.usf.model;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Student extends LoggablePerson {

    protected final Collection<String> responsibleIds = new ArrayList<>();

    public static Student fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final Student student = new Student();
        fillPersonDefaultImpl(resultSet, student);
        return student;
    }

    public static Student fromJson(String json) {
        return new Gson().fromJson(json, Student.class);
    }

    public Collection<String> getResponsibleIds() {
        return new ArrayList<>(responsibleIds);
    }

    public void addResponsible(String r) {
        responsibleIds.add(r);
    }

    public void addResponsibles(Collection<String> r) {
        responsibleIds.addAll(r);
    }

    public void removeResponsible(String r) {
        responsibleIds.remove(r);
    }

    @Override
    protected String defineRole() {
        return "student";
    }
}