package br.edu.usf.resource;

import br.edu.usf.dao.ReportCardDao;
import br.edu.usf.model.ReportCard;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/report_card")
public class ReportCardResource extends DefaultResource<ReportCard> {
    @Override
    public boolean insertImpl(ReportCard reportCard) {
        return ReportCardDao.gi().insert(reportCard);
    }

    @Override
    public Collection<ReportCard> findAllImpl() {
        return ReportCardDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(ReportCard reportCard) {
        return ReportCardDao.gi().update(reportCard);
    }

    @Override
    public boolean deleteImpl(ReportCard reportCard) {
        return ReportCardDao.gi().delete(reportCard);
    }

    @Override
    protected ReportCard convertInputImpl(String input) {
        return ReportCard.fromJson(input);
    }
}
