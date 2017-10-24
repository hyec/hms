package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.hms.model.User;
import project.hms.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository users;

    @Autowired
    public AdminController(UserRepository users) {
        this.users = users;
    }

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/user/list";
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> userList() {
        return users.findAll();
    }

}
