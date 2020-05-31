package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Responsible;
import br.edu.usf.model.Student;
import br.edu.usf.utils.InputUtils;
import br.edu.usf.utils.UserDaoUtils;
import org.jetbrains.annotations.NotNull;
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

public class StudentDao extends UserDao<Student> {
    private static final Logger logger = LoggerFactory.getLogger(ResponsibleDao.class);

    private static final StudentDao instance = new StudentDao();

    public static StudentDao gi() {
        return instance;
    }

    private StudentDao() {
        super();
    }

    @Override
    public boolean insert(Student student) {
        final String studentId = insertPersonDefaultImpl(student);
        if (InputUtils.validString(studentId)) {
            relateResponsibleWithStudents(studentId, student.getResponsible());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        final boolean update = super.update(student);
        if (update) {
            relateResponsibleWithStudents(student.getId(), student.getResponsible());
        }
        return update;
    }

    public Collection<Student> relatedTo(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible cannot be null");

        final String sql = "SELECT * FROM student s INNER JOIN responsible_student rs on s.id = rs.student_id AND rs.responsible_id = ?";
        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(responsible.getId()));

            ResultSet resultSet = s.executeQuery();

            Collection<Student> students = new ArrayList<>();

            while (resultSet.next()) {
                Student student = resultSetToPerson(resultSet);
                students.add(student);
            }

            return students;

        } catch (SQLException e) {
            logger.error("Error to search students related to a responsible");
        }
        return null;
    }

    private void relateResponsibleWithStudents(String studentId, Collection<Responsible> responsibles) {

        boolean successToClean = clearResponsibleWithStudents(studentId, responsibles);
        if (!successToClean) {
            return;
        }

        if (responsibles.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO responsible_student (responsible_id, student_id) VALUES (?, ?)";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Responsible responsible : responsibles) {
                s.setString(1, studentId);
                s.setString(2, responsible.getId());
                s.addBatch();
            }

            s.execute();

        } catch (SQLException e) {
            logger.error("Error to relate students with responsible in database", e);
        }
    }

    private boolean clearResponsibleWithStudents(String studentId, @NotNull Collection<Responsible> responsibles) {
        final boolean emptyRelation = responsibles.isEmpty();

        String sql = "DELETE FROM responsible_student WHERE student_id = ? ";
        if (!emptyRelation) {
            sql += "AND responsible_id NOT IN(?)";
        }

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(studentId));

            if (!emptyRelation) {
                StringBuilder builder = new StringBuilder();
                for (Responsible responsible : responsibles) {
                    builder.append(responsible.getId());
                }

                s.setString(2, builder.toString());
            }

            s.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to unrelate students with responsibles in database", e);
        }

        return false;
    }

    @Override
    protected Student resultSetToPerson(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        UserDaoUtils.resultSetToPerson(resultSet, student);

        return student;
    }

    @Override
    public String role() {
        return "student";
    }
}
