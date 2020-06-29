package br.edu.usf.model;

import br.edu.usf.dao.DisciplineDao;
import br.edu.usf.dao.EmployeeDao;
import br.edu.usf.dao.StudentDao;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class Class {

    private String id;
    private Discipline discipline;
    private Employee teacher;
    private Schedule schedule;
    private final Collection<Student> students = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Employee getTeacher() {
        return teacher;
    }

    public void setTeacher(Employee teacher) {
        this.teacher = teacher;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Collection<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudents(Collection<Student> students) {
        this.students.addAll(students);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public static Class fromResultSet(ResultSet resultSet) throws SQLException {
        final String[] daysOfWeeksArray = (String[]) resultSet.getArray("days_of_week").getArray();

        final UUID[] studentIdsArray = (UUID[]) resultSet.getArray("student_ids").getArray();
        final List<String> studentIds = Arrays.stream(studentIdsArray).map(UUID::toString).collect(Collectors.toList());

        final Schedule schedule = new Schedule();
        final Set<DayOfWeek> days_of_week = Arrays.stream(daysOfWeeksArray).map(DayOfWeek::valueOf).collect(Collectors.toSet());
        schedule.setDaysOfWeek(days_of_week);
        schedule.setTimeIni(resultSet.getTime("time_ini"));
        schedule.setTimeEnd(resultSet.getTime("time_end"));

        final Class aClass = new Class();
        aClass.setId(resultSet.getString("class_id"));
        aClass.setDiscipline(DisciplineDao.gi().findById(resultSet.getString("discipline_id")));
        aClass.setSchedule(schedule);
        aClass.setTeacher(EmployeeDao.gi().findById(resultSet.getString("teacher_id")));
        aClass.addStudents(StudentDao.gi().findById(studentIds));

        return aClass;
    }

    public static Class fromJson(String json) {
        return new Gson().fromJson(json, Class.class);
    }
}
