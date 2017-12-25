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

/**
 * 这是一个关于雇员的列表查看，信息查看，信息编辑和创建新雇员的类
 */
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

    /**
     * 这个函数展示了雇员的列表
     *
     * @return 雇员列表
     */
    @GetMapping({"", "/"})
    @Secured({"ROLE_MANAGER"})
    public String index() {
        return "redirect:/admin/employee/list";
    }

    /**
     * 针对employee list 的访问请求 在list函数中向model 中加入 一个employee类型的list进行绑定，方便在thymeleaf中使用th:each进行每一个employee对象的信息输出（还包含新建，编辑employee方法）
     * @param model 模型
     * @return 雇员列表
     */
    @GetMapping("/list")
    @Secured({"ROLE_MANAGER"})
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "admin/employee/list";
    }

    /**
     * 针对特定employee对象的信息访问 在info函数中请求使用注释在url中获得id对象，使用repository找到对象进行绑定，方便在thymeleaf中对特定employee对象进行信息输出
     * @param id 雇员id
     * @param model 模型
     * @return 雇员信息界面
     * @throws Exception 不存在对应的雇员
     */
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

    /**
     * 针对特定employee对象的信息更改访问请求 在edit函数中使用注释在url中获得id对象，若存在该对象则绑定该对象，不存在则绑定一个新对象，在edit界面中填写各个信息后进行employee 对象 post
     * @param id 雇员id
     * @param model 模型
     * @return 雇员信息编辑界面
     * @throws Exception 不存在对应的雇员
     */
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

    /**
     * 函数获得该对象后进行安全验证，如果不是新对象则在数据库中寻找对应旧对象，将信息更新后存入数据库，是新对象则直接存入数据库；
     * @param employee 被修改的雇员对象
     * @param result 是否有错误
     * @return 修改雇员信息界面
     */
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

    /**
     * 针对创建新employee对象的请求，在展现用户列表后可以通过newpost函数将用户添加为新雇员（类型为清洁员）
     * @param id 用户id
     * @return 对应用户信息编辑界面
     * @throws Exception 不存在对应的用户
     */
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
