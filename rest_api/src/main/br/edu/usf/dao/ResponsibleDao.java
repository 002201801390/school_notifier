package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Responsible;
import br.edu.usf.model.Student;
import br.edu.usf.utils.DaoUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ResponsibleDao implements Dao<Responsible> {
    private static final Logger logger = LoggerFactory.getLogger(ResponsibleDao.class);

    private static final ResponsibleDao instance = new ResponsibleDao();

    public static ResponsibleDao gi() {
        return instance;
    }

    @Override
    public boolean insert(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");

        String sql = "INSERT INTO responsible (cpf, name, login, password, email, dt_birth, phone) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            fillStatementWithResponsible(responsible, s);

            boolean success = s.execute();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                String id = generatedKeys.getString("id");
                relateResponsibleWithStudents(id, responsible.getStudents());
            }

            return success;

        } catch (SQLException e) {
            logger.error("Error to insert responsible in database", e);
        }
        return false;
    }

    @Override
    public Collection<Responsible> findAll() {
        String sql = "SELECT id, cpf, name, login, password, email, dt_birth, phone FROM responsible";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            ResultSet resultSet = s.executeQuery();

            Collection<Responsible> responsible = new ArrayList<>();

            while (resultSet.next()) {
                Responsible r = new Responsible();
                r.setId(resultSet.getString("id"));
                r.setCpf(resultSet.getString("cpf"));
                r.setName(resultSet.getString("name"));
                r.setLogin(resultSet.getString("login"));
                r.setPassword(resultSet.getString("password"));
                r.setEmail(resultSet.getString("email"));
                r.setPhone(resultSet.getString("phone"));

                Date dtBirth = resultSet.getDate("dtBirth");
                if (dtBirth != null) {
                    r.setDtBirth(dtBirth.toLocalDate());
                }

                responsible.add(r);
            }

            return responsible;

        } catch (SQLException e) {
            logger.error("Error to search for responsible in database", e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");

        String sql = "UPDATE responsible set cpf = ?, name = ?, login = ?, password = ?, email = ?, dt_birth = ?, phone = ? WHERE id = ?";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            fillStatementWithResponsible(responsible, s);
            s.setString(8, responsible.getId());

            return s.execute();

        } catch (SQLException e) {
            logger.error("Error to search for responsible in database", e);
        }
        return false;
    }

    private static void fillStatementWithResponsible(@NotNull Responsible responsible, @NotNull PreparedStatement s) throws SQLException {
        s.setString(1, responsible.getCpf());
        s.setString(2, responsible.getName());
        s.setString(3, responsible.getLogin());
        s.setString(4, responsible.getPassword());
        s.setString(5, responsible.getEmail());
        s.setDate(6, DaoUtils.convertDateField(responsible.getDtBirth()));
        s.setString(7, responsible.getPhone());
    }

    @Override
    public boolean delete(Responsible responsible) {
        Objects.requireNonNull(responsible, "Responsible can't be null");

        String sql = "DELETE FROM responsible WHERE id = ? ";

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setString(1, responsible.getId());

            return s.execute();

        } catch (SQLException e) {
            logger.error("Error to delete responsible with");
        }

        return false;
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

            return s.execute();

        } catch (SQLException e) {
            logger.error("Error to relate responsible with students in database", e);
        }

        return false;
    }

}