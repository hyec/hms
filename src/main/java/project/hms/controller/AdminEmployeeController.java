package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Employee;
import project.hms.model.User;
import project.hms.model.enums.EmployeeType;
import project.hms.repository.EmployeeRepository;
import project.hms.repository.UserRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/employee")
public class AdminEmployeeController {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminEmployeeController(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    @Secured({"ROLE_MANAGER"})
    public String index() {
        return "redirect:/admin/employee/list";
    }

    @GetMapping("/list")
    @Secured({"ROLE_MANAGER"})
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "admin/employee/list";
    }

    @GetMapping("/info")
    @Secured({"ROLE_MANAGER"})
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("employee", employeeOptional.get());
        return "admin/employee/info";
    }


    @GetMapping("/edit")
    @Secured({"ROLE_MANAGER"})
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<Employee> employeeOptional = employeeRepository.findById(id);
            if (!employeeOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("employee", employeeOptional.get());
        } else {
            model.addAttribute("employee", new Employee());
        }

        return "admin/employee/edit";
    }

    @PostMapping("/edit")
    @Secured({"ROLE_MANAGER"})
    public String editPOST(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {
        Employee savedEmployee;
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        if (employeeOptional.isPresent()) {
            savedEmployee = employeeOptional.get();
            ModelTool.merge(employee, savedEmployee);
            employee = savedEmployee;
        }
        savedEmployee = employeeRepository.save(employee);
        return "redirect:/admin/employee/info?id=" + savedEmployee.getId();
    }

    @PostMapping("/new")
    @Secured({"ROLE_MANAGER"})
    public String newPOST(@RequestParam("user") Integer id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new Exception("Error1");
        }
        Employee employee = userOptional.get().getEmployee();
        if (employee != null) {
            throw new Exception("Error2");
        }
        employee = new Employee();
        employee.setUser(userOptional.get());
        employee.setPerformance(0);
        employee.setType(EmployeeType.CLEANER);
        employee = employeeRepository.save(employee);
        return "redirect:/admin/employee/edit?id=" + employee.getId();
    }
}
