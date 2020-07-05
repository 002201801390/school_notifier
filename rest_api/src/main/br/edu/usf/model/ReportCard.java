package br.edu.usf.model;

import br.edu.usf.dao.ClassDao;
import br.edu.usf.dao.StudentDao;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ReportCard {

    private String id;
    private Student student;
    private Class clazz;
    private float score;
    private boolean responsibleAck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isResponsibleAck() {
        return responsibleAck;
    }

    public void setResponsibleAck(boolean responsibleAck) {
        this.responsibleAck = responsibleAck;
    }

    public static ReportCard fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final ReportCard student = new ReportCard();
        student.setId(resultSet.getString("id"));
        student.setStudent(StudentDao.gi().findById(resultSet.getString("student_id")));
        student.setClazz(ClassDao.gi().findById(resultSet.getString("class_id")));
        student.setScore(resultSet.getFloat("score"));
        student.setResponsibleAck(resultSet.getBoolean("responsible_ack"));
        return student;
    }

    public static ReportCard fromJson(String json) {
        return new Gson().fromJson(json, ReportCard.class);
    }
}
