package br.edu.usf;

import br.edu.usf.dao.EmployeeDao;
import br.edu.usf.dao.ResponsibleDao;
import br.edu.usf.dao.StudentDao;
import br.edu.usf.model.Employee;
import br.edu.usf.model.Responsible;
import br.edu.usf.model.Student;
import br.edu.usf.utils.Randomization;

public class FillWithUsers {
    public static void main(String[] args) {
        // Employees
        for (int i = 0; i < 10; i++) {
            final Employee employee = new Employee();
            Randomization.fillWithRandomPerson(employee);
            EmployeeDao.gi().insert(employee);
        }

        // Responsible
        for (int i = 0; i < 10; i++) {
            final Responsible responsible = new Responsible();
            Randomization.fillWithRandomPerson(responsible);
            ResponsibleDao.gi().insert(responsible);
        }

        // Students
        for (int i = 0; i < 10; i++) {
            final Student student = new Student();
            Randomization.fillWithRandomPerson(student);
            StudentDao.gi().insert(student);
        }

    }
}
