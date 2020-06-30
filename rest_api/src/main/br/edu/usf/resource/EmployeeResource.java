package br.edu.usf.resource;

import br.edu.usf.dao.EmployeeDao;
import br.edu.usf.model.Employee;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/employee")
public class EmployeeResource extends DefaultResource<Employee> {

    @Override
    public boolean insertImpl(Employee employee) {
        return EmployeeDao.gi().insert(employee);
    }

    @Override
    public Collection<Employee> findAllImpl() {
        return EmployeeDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Employee employee) {
        return EmployeeDao.gi().update(employee);
    }

    @Override
    public boolean deleteImpl(Employee employee) {
        return EmployeeDao.gi().delete(employee);
    }

    @Override
    protected Employee convertInputImpl(String input) {
        return Employee.fromJson(input);
    }
}
