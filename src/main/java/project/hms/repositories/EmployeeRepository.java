package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.Employee;
import project.hms.models.User;
import project.hms.models.enums.EmployeeType;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByType(EmployeeType type);

    List<Employee> findAllByPerformance(int performance);

    List<Employee> findByUser(User user);
}
