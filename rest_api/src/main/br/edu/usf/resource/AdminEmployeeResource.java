package br.edu.usf.resource;

import br.edu.usf.dao.AdminEmployeeDao;
import br.edu.usf.model.AdminEmployee;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/admin-employee")
public class AdminEmployeeResource extends DefaultResource<AdminEmployee> {

    @Override
    public boolean insertImpl(AdminEmployee adminEmployee) {
        return AdminEmployeeDao.gi().insert(adminEmployee);
    }

    @Override
    public Collection<AdminEmployee> findAllImpl() {
        return AdminEmployeeDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(AdminEmployee adminEmployee) {
        return AdminEmployeeDao.gi().update(adminEmployee);
    }

    @Override
    public boolean deleteImpl(AdminEmployee adminEmployee) {
        return AdminEmployeeDao.gi().update(adminEmployee);
    }

    @Override
    protected AdminEmployee convertInput(String input) {
        return AdminEmployee.fromJson(input);
    }
}
