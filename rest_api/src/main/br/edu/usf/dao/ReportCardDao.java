package br.edu.usf.dao;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.ReportCard;
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

public class ReportCardDao implements Dao<ReportCard> {
    private static final Logger log = LoggerFactory.getLogger(ReportCardDao.class);

    private static final ReportCardDao instance = new ReportCardDao();

    public static ReportCardDao gi() {
        return instance;
    }

    @Override
    public boolean insert(ReportCard reportCard) {
        Objects.requireNonNull(reportCard, "Report Card cannot be null");

        final String sql = "INSERT INTO report_cards(student_id, class_id, score, responsible_ack) VALUES (?, ?, ?, ?)";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, UUID.fromString(reportCard.getStudent().getId()));
            s.setObject(2, UUID.fromString(reportCard.getClazz().getId()));
            s.setFloat(3, reportCard.getScore());
            s.setBoolean(4, reportCard.isResponsibleAck());

            s.execute();

            final ResultSet resultSet = s.getGeneratedKeys();
            if (resultSet.next()) {
                final String id = resultSet.getString("id");
                reportCard.setId(id);

                return true;
            }
        } catch (SQLException e) {
            log.error("Error to insert Report Card", e);
        }
        return false;
    }

    @Override
    public Collection<ReportCard> findAll() {
        final String sql = "SELECT * FROM report_cards";

        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            final ResultSet resultSet = s.executeQuery();

            final Collection<ReportCard> reportCards = new ArrayList<>();

            while (resultSet.next()) {
                final ReportCard reportCard = ReportCard.fromResultSetImpl(resultSet);
                reportCards.add(reportCard);
            }
            return reportCards;

        } catch (SQLException e) {
            log.error("Error to search Report Cards", e);
        }
        return null;
    }

    @Override
    public boolean update(ReportCard reportCard) {
        Objects.requireNonNull(reportCard, "Report card cannot be null!");

        final String sql = "UPDATE report_cards SET class_id = ?, student_id = ?, score = ?,  responsible_ack = ? WHERE id = ?";
        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(reportCard.getClazz().getId()));
            s.setObject(2, UUID.fromString(reportCard.getStudent().getId()));
            s.setFloat(3, reportCard.getScore());
            s.setBoolean(4, reportCard.isResponsibleAck());
            s.setObject(5, UUID.fromString(reportCard.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            log.error("Error to update Report Card", e);
        }
        return false;
    }

    @Override
    public boolean delete(ReportCard reportCard) {
        Objects.requireNonNull(reportCard, "Report card cannot be null!");

        final String sql = "DELETE FROM report_cards WHERE id = ?";
        try (final PreparedStatement s = DBConnection.gi().connection().prepareStatement(sql)) {
            s.setObject(1, UUID.fromString(reportCard.getId()));

            s.execute();

            return true;

        } catch (SQLException e) {
            log.error("Error to delete Report Card", e);
        }
        return false;
    }
}
