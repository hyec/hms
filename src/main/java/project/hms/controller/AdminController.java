package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.repository.UserRepository;

/**
 * 后台首页控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository users;

    @Autowired
    public AdminController(UserRepository users) {
        this.users = users;
    }

    /**
     * 首页
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/user/list";
    }

}
