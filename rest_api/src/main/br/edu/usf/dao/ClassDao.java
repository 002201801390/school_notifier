package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Class;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class ClassDao implements Dao<Class> {
    private static final Logger log = LoggerFactory.getLogger(ClassDao.class);

    private static final ClassDao instance = new ClassDao();

    public static ClassDao gi() {
        return instance;
    }

    @Override
    public boolean insert(Class aClass) {
        Objects.requireNonNull(aClass, "Class cannot be null");

        final String sql = "INSERT INTO classes(discipline_id, teacher_id, days_of_week, time_ini, time_end) VALUES (?, ?, ?, ?, ?)";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, UUID.fromString(aClass.getDiscipline().getId()));
            s.setObject(2, UUID.fromString(aClass.getTeacher().getId()));
            s.setArray(3, DBConnection.gi().connection().createArrayOf("DAYS_OF_WEEK", aClass.getSchedule().getDaysOfWeekAsArray()));
            s.setTime(4, aClass.getSchedule().getTimeIni());
            s.setTime(5, aClass.getSchedule().getTimeEnd());

            s.execute();

            final ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                final String id = generatedKeys.getString("id");
                aClass.setId(id);

                return true;
            }
        } catch (SQLException e) {
            log.error("Error to insert Class", e);
        }

        return false;
    }

    @Override
    public Collection<Class> findAll() {

        final String sql = "SELECT " +
                "c.id            AS class_id, " +
                "c.discipline_id AS discipline_id, " +
                "c.teacher_id    AS teacher_id, " +
                "c.days_of_week  AS days_of_week, " +
                "c.time_ini      AS time_ini, " +
                "c.time_end      AS time_end, " +
                "array_agg(s.id) AS student_ids " +
                "FROM classes_students cs " +
                "INNER JOIN classes c on cs.class_id = c.id " +
                "INNER JOIN student s on cs.student_id = s.id " +
                "GROUP BY c.id";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            final ResultSet resultSet = s.executeQuery();

            final Collection<Class> classes = new ArrayList<>();

            while (resultSet.next()) {
                Class acClass = Class.fromResultSet(resultSet);
                classes.add(acClass);
            }
            return classes;

        } catch (SQLException e) {
            log.error("Error to search for all classes", e);
        }

        return null;
    }

    @Override
    public boolean update(Class aClass) {
        Objects.requireNonNull(aClass, "Class cannot be null!");

        final String sql = "";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {


        } catch (SQLException e) {
            log.error("Error to update Class", e);
        }

        return false;
    }

    @Override
    public boolean delete(Class aClass) {
        Objects.requireNonNull(aClass, "Class cannot be null!");

        final String sql = "DELETE FROM classes_students WHERE class_id = ?";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {


        } catch (SQLException e) {
            log.error("Error to delete Class", e);
        }

        return false;
    }
}