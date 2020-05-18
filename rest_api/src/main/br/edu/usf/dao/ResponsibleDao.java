package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Responsible;
import br.edu.usf.model.Student;
import br.edu.usf.utils.UserDaoUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class ResponsibleDao extends UserDao<Responsible> {
    private static final Logger logger = LoggerFactory.getLogger(ResponsibleDao.class);

    private static final ResponsibleDao instance = new ResponsibleDao();

    public static ResponsibleDao gi() {
        return instance;
    }

    private ResponsibleDao() {
        super();
    }

    @Override
    public boolean insert(Responsible responsible) {
        final String personId = insertPersonDefaultImpl(responsible);
        if (personId != null) {
            relateResponsibleWithStudents(personId, responsible.getStudents());
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Responsible responsible) {
        final boolean update = super.update(responsible);
        if (update) {
            relateResponsibleWithStudents(responsible.getId(), responsible.getStudents());
        }

        return update;
    }

    private void relateResponsibleWithStudents(String responsibleId, Collection<Student> students) {

        boolean successToClean = clearResponsibleWithStudents(responsibleId, students);
        if (!successToClean) {
            return;
        }

        String sql = "INSERT INTO responsible_student (responsible_id, student_id) VALUES (?, ?)";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Student student : students) {
                s.setString(1, responsibleId);
                s.setString(2, student.getId());
                s.addBatch();
            }

            s.execute();

        } catch (SQLException e) {
            logger.error("Error to relate responsible with students in database", e);
        }
    }

    private boolean clearResponsibleWithStudents(String responsibleId, @NotNull Collection<Student> students) {
        final boolean emptyRelation = students.isEmpty();

        String sql = "DELETE FROM responsible_student WHERE responsible_id = ? ";
        if (!emptyRelation) {
            sql += "AND id_student NOT IN(?)";
        }

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setString(1, responsibleId);

            if (!emptyRelation) {
                StringBuilder builder = new StringBuilder();
                for (Student student : students) {
                    builder.append(student.getId());
                }

                s.setString(2, builder.toString());
            }

            s.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to relate responsible with students in database", e);
        }

        return false;
    }

    @Override
    protected Responsible resultSetToPerson(@NotNull ResultSet resultSet) throws SQLException {
        final Responsible responsible = new Responsible();

        UserDaoUtils.resultSetToPerson(resultSet, responsible);

        return responsible;
    }

    @Override
    public String role() {
        return "responsible";
    }
}