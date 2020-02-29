package com.github.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;

import com.github.database.DBConnection;
import com.github.logger.Log;
import com.github.model.Responsible;
import com.github.model.Student;
import com.github.utils.DaoUtils;

public class ResponsibleDao implements Dao<Responsible> {

    @Override
    public boolean insert(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");

        String sql = "INSERT INTO responsibles "
                + "(cpf, name, login, password, email, dtBirth, phone) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement s = DBConnection.connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            s.setString(1, responsible.getCpf());
            s.setString(2, responsible.getName());
            s.setString(3, responsible.getLogin());
            s.setString(4, responsible.getPassword());
            s.setString(5, responsible.getEmail());
            s.setDate  (6, DaoUtils.convertDateField(responsible.getDtBirth()));
            s.setString(7, responsible.getPhone());

            s.executeQuery();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt("id");
                relateResposibleWithStudents(id, responsible.getStudents());
            }

            return true;

        } catch (SQLException e) {
            Log.ERRO(getClass(), "Error to insert responsible in database", e);
        }
        return false;
    }

    @Override
    public Collection<Responsible> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");
        
        // TODO Auto-generated method stub
        return false;
    }

    private boolean relateResposibleWithStudents(int responsibleId, Collection<Student> students) {

        boolean successToClean = clearResposibleWithStudents(responsibleId, students);
        if (!successToClean) {
            return false;
        }

        String sql = "INSERT INTO responsibles_students "
        + "(id_responsible, id_student) VALUES "
        + "(?, ?)";

        try (PreparedStatement s = DBConnection.connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Student student : students) {
                s.setInt(1, responsibleId);
                s.setInt(2, student.getId());
                s.addBatch();
            }

            return s.execute();

        } catch (SQLException e) {
            Log.ERRO(getClass(), "Error to relate responsible with students in database", e);
        }

        return false;
    }

    private boolean clearResposibleWithStudents(int responsibleId, Collection<Student> students) {
        final boolean emptyRelation = students.isEmpty();

        String sql = "DELETE FROM responsibles_students WHERE id_responsible = ? ";
        if (!emptyRelation) {
            sql += "AND id_student NOT IN(?)";
        }
        
        try (PreparedStatement s = DBConnection.connection().prepareStatement(sql)) {
            s.setInt(1, responsibleId);

            if (!emptyRelation) {
                StringBuilder builder = new StringBuilder();
                for (Student student : students) {
                    builder.append(student.getId());
                }

                s.setString(2, builder.toString());
            }

            return s.execute();

        } catch (SQLException e) {
            Log.ERRO(getClass(), "Error to relate responsible with students in database", e);
        }

        return false;
    }

}