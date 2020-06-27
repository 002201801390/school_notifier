package br.edu.usf.model;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Discipline {

    private String id;
    private String name;

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
        this.name = name;
    }

    public static Discipline fromResultSet(ResultSet resultSet) throws SQLException {
        final Discipline discipline = new Discipline();
        discipline.setId(resultSet.getString("id"));
        discipline.setName(resultSet.getString("name"));

        return discipline;
    }

    public static Discipline fromJson(String json) {
        return new Gson().fromJson(json, Discipline.class);
    }
}
