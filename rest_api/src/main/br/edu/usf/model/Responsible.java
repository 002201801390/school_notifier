package br.edu.usf.model;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Responsible extends LoggablePerson {

    protected final Collection<String> students = new ArrayList<>();

    public static Responsible fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final Responsible responsible = new Responsible();
        fillPersonDefaultImpl(resultSet, responsible);
        return responsible;
    }

    public static Responsible fromJson(String json) {
        return new Gson().fromJson(json, Responsible.class);
    }

    public Collection<String> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(String s) {
        students.add(s);
    }

    public void addStudents(Collection<String> s) {
        students.addAll(s);
    }

    public void removeStudent(String s) {
        students.remove(s);
    }

    @Override
    protected String defineRole() {
        return "responsible";
    }
}