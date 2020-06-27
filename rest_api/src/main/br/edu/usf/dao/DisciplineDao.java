package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Discipline;
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

public class DisciplineDao implements Dao<Discipline> {
    private static final Logger log = LoggerFactory.getLogger(DisciplineDao.class);

    private static final DisciplineDao instance = new DisciplineDao();

    public static DisciplineDao gi() {
        return instance;
    }

    @Override
    public boolean insert(Discipline discipline) {
        Objects.requireNonNull(discipline, "Discipline cannot be null!");

        final String sql = "INSERT INTO disciplines(name) VALUES (?)";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, discipline.getName());

            s.execute();

            final ResultSet resultSet = s.getGeneratedKeys();
            if (resultSet.next()) {
                final String id = resultSet.getString("id");
                discipline.setId(id);

                return true;
            }
        } catch (SQLException e) {
            log.error("Error to insert Discipline", e);
        }

        return false;
    }

    @Override
    public Collection<Discipline> findAll() {

        final String sql = "SELECT * FROM disciplines";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            final ResultSet resultSet = s.executeQuery();

            final Collection<Discipline> disciplines = new ArrayList<>();

            while (resultSet.next()) {
                final Discipline discipline = Discipline.fromResultSet(resultSet);
                disciplines.add(discipline);
            }

            return disciplines;

        } catch (SQLException e) {
            log.error("Error to search for all disciplines", e);
        }

        return null;
    }

    @Override
    public boolean update(Discipline discipline) {
        Objects.requireNonNull(discipline, "Discipline cannot be null!");

        final String sql = "UPDATE disciplines SET name = ? WHERE id = ?";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setString(1, discipline.getName());
            s.setObject(2, UUID.fromString(discipline.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            log.error("Error to update discipline", e);
        }

        return false;
    }

    @Override
    public boolean delete(Discipline discipline) {
        Objects.requireNonNull(discipline, "Discipline cannot be null!");

        final String sql = "DELETE FROM disciplines WHERE id = ?";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(discipline.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            log.error("Error to update discipline", e);
        }

        return false;
    }
}
