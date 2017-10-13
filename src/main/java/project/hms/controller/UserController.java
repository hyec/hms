package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.hms.model.User;
import project.hms.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository users;

    @Autowired
    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public User index(Principal principal) {
        return users.findByUsername(principal.getName());
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
