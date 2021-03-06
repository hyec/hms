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
import project.hms.model.GoodOrder;
import project.hms.model.Order;
import project.hms.model.User;
import project.hms.model.enums.GoodsOrderStatus;
import project.hms.model.enums.OrderStatus;
import project.hms.repository.GoodOrderRepository;
import project.hms.repository.OrderRepository;
import project.hms.repository.UserRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * 用户界面控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository users;
    private final OrderRepository orders;
    private final GoodOrderRepository gorders;

    @Autowired
    public UserController(UserRepository users, OrderRepository orders, GoodOrderRepository gorders) {
        this.users = users;
        this.orders = orders;
        this.gorders = gorders;
    }

    /**
     * 用户页首页，跳转到用户个人信息
     */
    @GetMapping({"", "/"})
    @PreAuthorize("isAuthenticated()")
    public String index() {
        return "redirect:/user/info";
    }

    /**
     * 用户登陆页，返回登陆页模板
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 用户注册页，返回注册页模板
     */
    @GetMapping("/register")
    public String register(UserDto userDto) {
        return "register";
    }

    /**
     * 用户注册POST响应，验证表单和去重，并将用户信息插入到数据库
     *
     * @param userDto 用户注册的表单
     */
    @PostMapping("/register")
    public String registerPOST(@Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            result.rejectValue("password", "validate.password.mismatch", "密码不匹配！");
            return "register";
        }

        Optional<User> userOptional = users.findByUsernameIgnoreCase(userDto.getUsername());

        if (userOptional.isPresent()) {
            result.rejectValue("username", "validate.user.exists", "用户名已经存在！");
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

    /**
     * 用户信息页，显示用户信息和未完成订单与订餐信息
     *
     * @param principal 用户认证信息
     */
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public String info(Principal principal, Model model) {
        User user = users.findByUsername(principal.getName());
        model.addAttribute("user", user);

        List<Order> orderList = orders.findAllByOwner(user);
        orderList.removeIf(e -> {
            if (e.getStatus() == OrderStatus.UNPAID)
                return false;
            if (e.getStatus() == OrderStatus.PAID)
                return false;
            if (e.getStatus() == OrderStatus.CHECK_IN)
                return false;
            return true;
        });
        model.addAttribute("orders", orderList);

        List<GoodOrder> gorderList = gorders.findAllByOwner(user);
        gorderList.removeIf(e -> {
            if (e.getStatus() == GoodsOrderStatus.COMPLETED)
                return true;
            return false;
        });
        model.addAttribute("gorders", gorderList);

        return "user/info";
    }

    /**
     * 用户个人信息编辑
     * @param principal 用户认证信息
     */
    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String edit(Principal principal, Model model) {
        model.addAttribute("user", users.findByUsername(principal.getName()));
        return "user/edit";
    }

    /**
     * 用户信息编辑POST响应
     * @param user 用户信息表单
     * @param principal 用户认证信息
     */
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
