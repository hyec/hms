package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.hms.data.dto.UserDto;
import project.hms.model.User;
import project.hms.repository.UserRepository;

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
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public User index(Principal principal) {
        return users.findByUsername(principal.getName());
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

    @GetMapping("/edit")
    public String edit() {
        return "user/info";
    }


    @PostMapping("/edit")
    public String editPOST(@Valid User user, BindingResult result,
                           Principal principal) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        User oldUser = users.findByUsername(principal.getName());
        oldUser.setGender(user.getGender());
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());
        users.save(oldUser);
        return "user/info";
    }

}
