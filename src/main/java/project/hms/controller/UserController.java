package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.data.dto.UserDto;
import project.hms.model.User;
import project.hms.repository.UserRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository users;

    @Autowired
    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping({"", "/"})
    @PreAuthorize("isAuthenticated()")
    public String index() {
        return "redirect:/user/info";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(UserDto userDto) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPOST(@Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        Optional<User> userOptional = users.findByUsernameIgnoreCase(userDto.getUsername());

        if (userOptional.isPresent()) {
            result.rejectValue("username", "Username already exists.");
            return "register";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setIdNum(userDto.getIdNum());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userDto.getPassword()));

        users.save(user);

        return "redirect:/user/login";
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public String info(Principal principal, Model model) {
        model.addAttribute("user", users.findByUsername(principal.getName()));
        return "user/info";
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String edit(Principal principal, Model model) {
        model.addAttribute("user", users.findByUsername(principal.getName()));
        return "user/edit";
    }


    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String editPOST(@Valid User user, BindingResult result,
                           Principal principal) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        User savedUser;
        savedUser = users.findByUsername(principal.getName());
        ModelTool.merge(user, savedUser);
        users.save(savedUser);
        return "redirect:/user/info";
    }

}
