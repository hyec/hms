package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Employee;
import project.hms.model.User;
import project.hms.model.enums.EmployeeType;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    /**
     * 按Id查找雇员
     */
    Optional<Employee> findById(int id);

    /**
     * 按类型查找雇员
     */
    List<Employee> findAllByType(EmployeeType type);

    /**
     *按绩效查找雇员
     */
    List<Employee> findAllByPerformance(int performance);

    /**
     *按照用户查找雇员
     */
    List<Employee> findByUser(User user);
}
