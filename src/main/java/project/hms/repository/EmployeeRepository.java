package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Employee;
import project.hms.model.User;
import project.hms.model.enums.EmployeeType;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByType(EmployeeType type);

    List<Employee> findAllByPerformance(int performance);

    List<Employee> findByUser(User user);
}
