package br.edu.usf.resource;

import br.edu.usf.dao.ResponsibleDao;
import br.edu.usf.model.Responsible;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/responsible")
public class ResponsibleResource extends DefaultResource<Responsible> {

    @Override
    public boolean insertImpl(Responsible responsible) {
        return ResponsibleDao.gi().insert(responsible);
    }

    @Override
    public Collection<Responsible> findAllImpl() {
        return ResponsibleDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Responsible responsible) {
        return ResponsibleDao.gi().update(responsible);
    }

    @Override
    public boolean deleteImpl(Responsible responsible) {
        return ResponsibleDao.gi().update(responsible);
    }
}
