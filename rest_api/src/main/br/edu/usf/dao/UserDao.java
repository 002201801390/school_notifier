package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.LoggablePerson;
import br.edu.usf.utils.DaoUtils;
import br.edu.usf.utils.InputUtils;
import br.edu.usf.utils.UserDaoUtils;
import org.jetbrains.annotations.Nullable;
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

public abstract class UserDao<T extends LoggablePerson> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Override
    public boolean insert(T person) {
        return InputUtils.validString(insertPersonDefaultImpl(person));
    }

    protected final @Nullable String insertPersonDefaultImpl(T person) {
        Objects.requireNonNull(person, "Person can't be null");

        final String sql = UserDaoUtils.insertQuery(role());

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            defaultFillStatement(person, s);

            s.execute();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                final String personId = generatedKeys.getString("id");
                person.setId(personId);

                return personId;
            }

        } catch (SQLException e) {
            logger.error("Error to insert person in database", e);
        }
        return null;
    }

    @Override
    public final @Nullable Collection<T> findAll() {
        final String sql = UserDaoUtils.selectQuery(role());

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            ResultSet resultSet = s.executeQuery();

            Collection<T> persons = new ArrayList<>();

            while (resultSet.next()) {
                T person = resultSetToPerson(resultSet);
                persons.add(person);
            }

            return persons;

        } catch (SQLException e) {
            logger.error("Error to search for people in database", e);
        }
        return null;
    }

    @Override
    public boolean update(T person) {
        Objects.requireNonNull(person, "Person can't be null");

        final String sql = UserDaoUtils.updateQuery();

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            defaultFillStatement(person, s);
            s.setObject(8, UUID.fromString(person.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to search for person in database", e);
        }
        return false;
    }

    @Override
    public final boolean delete(T person) {
        Objects.requireNonNull(person, "Person can't be null");

        final String sql = UserDaoUtils.deleteQuery();

        try (PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(person.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to delete responsible with");
        }

        return false;
    }

    protected final void defaultFillStatement(T person, PreparedStatement statement) throws SQLException {
        Objects.requireNonNull(person, "Person cannot be null!");
        Objects.requireNonNull(statement, "Statement cannot be null!");

        statement.setString(1, person.getCpf());
        statement.setString(2, person.getName());
        statement.setString(3, person.getLogin());
        statement.setString(4, person.getPassword());
        statement.setString(5, person.getEmail());
        statement.setDate(6, DaoUtils.convertDateField(person.getDtBirth()));
        statement.setString(7, person.getPhone());
    }

    protected abstract T resultSetToPerson(ResultSet resultSet) throws SQLException;

    public abstract String role();
}
