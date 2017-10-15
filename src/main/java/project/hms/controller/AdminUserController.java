package project.hms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.User;
import project.hms.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    private final UserRepository repository;

    @Autowired
    public AdminUserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", repository.findAll());
        return "user/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<User> userOptional = repository.findById(id);
        if (!userOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("user", userOptional.get());
        return "user/info";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<User> userOptional = repository.findById(id);
            if (!userOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("user", userOptional.get());
        } else {
            model.addAttribute("user", new User());
        }

        return "user/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@ModelAttribute("user") @Valid User user, BindingResult result) {

        User savedUser = repository.save(user);
        return "redirect:/admin/room/info?id=" + savedUser.getId();
    }

}
