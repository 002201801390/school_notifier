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
import java.util.*;
import java.util.stream.Collectors;

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
            relateResponsibleWithStudents(studentId, student.getResponsibleIds());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        final boolean update = super.update(student);
        if (update) {
            relateResponsibleWithStudents(student.getId(), student.getResponsibleIds());
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

    private void relateResponsibleWithStudents(String studentId, Collection<String> responsibleIds) {

        boolean successToClean = clearResponsibleWithStudents(studentId, responsibleIds);
        if (!successToClean) {
            return;
        }

        if (responsibleIds.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO responsible_student (responsible_id, student_id) VALUES (?, ?)";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (String responsibleId : responsibleIds) {
                s.setString(1, studentId);
                s.setString(2, responsibleId);
                s.addBatch();
            }

            s.execute();

        } catch (SQLException e) {
            logger.error("Error to relate students with responsible in database", e);
        }
    }

    private boolean clearResponsibleWithStudents(String studentId, @NotNull Collection<String> responsibleIds) {
        final boolean emptyRelation = responsibleIds.isEmpty();

        String sql = "DELETE FROM responsible_student WHERE student_id = ? ";
        if (!emptyRelation) {
            sql += "AND responsible_id NOT IN(?)";
        }

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(studentId));

            if (!emptyRelation) {
                StringBuilder builder = new StringBuilder();
                for (String responsibleId : responsibleIds) {
                    builder.append(responsibleId);
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
        final UUID[] responsibleIdsArray = (UUID[]) resultSet.getArray("responsible_ids").getArray();
        final List<String> responsibleIds = Arrays.stream(responsibleIdsArray).map(UUID::toString).collect(Collectors.toList());

        student.addResponsibles(responsibleIds);

        return student;
    }

    @Override
    public String role() {
        return "student";
    }
}
