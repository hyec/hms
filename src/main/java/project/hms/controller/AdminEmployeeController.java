package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Employee;
import project.hms.repository.EmployeeRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/employee")
public class AdminEmployeeController {
    private final EmployeeRepository repository;

    @Autowired
    public AdminEmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employees", repository.findAll());
        return "employee/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Employee> employeeOptional = repository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("employee", employeeOptional.get());
        return "employee/info";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<Employee> employeeOptional = repository.findById(id);
            if (!employeeOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("employee", employeeOptional.get());
        } else {
            model.addAttribute("employee", new Employee());
        }

        return "employee/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {

        Employee saveEmployee = repository.save(employee);
        return "redirect:/admin/employee/info?id=" + saveEmployee.getId();
    }
}
