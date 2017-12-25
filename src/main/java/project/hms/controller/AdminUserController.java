package project.hms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.data.dto.SelectDto;
import project.hms.model.User;
import project.hms.repository.UserRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 后台用户管理控制类
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    private final UserRepository repository;

    @Autowired
    public AdminUserController(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * 用户管理首页，跳转到用户列表
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/user/list";
    }

    /**
     * 用户列表，SelectDto#select为true时显示为用户选择页面
     *
     * @param selectDto 选择信息表单
     * @see SelectDto
     */
    @GetMapping("/list")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String list(@ModelAttribute SelectDto selectDto, Model model) {
        if (selectDto == null) {
            selectDto = new SelectDto();
        }

        model.addAttribute("sel", selectDto);
        model.addAttribute("users", repository.findAll());
        return "admin/user/list";
    }

    /**
     * 显示用户信息
     *
     * @param id 用户id
     * @throws Exception 用户id不存在
     */
    @GetMapping("/info")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<User> userOptional = repository.findById(id);
        if (!userOptional.isPresent()) {
            throw new Exception("用户id不存在！");
        }

        model.addAttribute("user", userOptional.get());
        return "admin/user/info";
    }

    /**
     * 用户编辑/添加页面
     * @param id 用户id，为空时添加用户
     * @throws Exception 用户id不存在
     */
    @GetMapping("/edit")
    @Secured("ROLE_MANAGER")
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<User> userOptional = repository.findById(id);
            if (!userOptional.isPresent()) {
                throw new Exception("用户id不存在！");
            }

            model.addAttribute("user", userOptional.get());
        } else {
            model.addAttribute("user", new User());
        }

        return "admin/user/edit";
    }

    /**
     * 用户编辑/添加POST响应
     * @param user 用户id，为空时添加新用户
     */
    @PostMapping("/edit")
    @Secured("ROLE_MANAGER")
    public String editPOST(@ModelAttribute("user") @Valid User user, BindingResult result) {
        User savedUser;
        Optional<User> userOptional = repository.findById(user.getId());

        if (user.getPassword().length() > 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }

        if (userOptional.isPresent()) {
            savedUser = userOptional.get();
            ModelTool.merge(user, savedUser);
            user = savedUser;
        }

        savedUser = repository.save(user);
        return "redirect:/admin/user/info?id=" + savedUser.getId();
    }

}
