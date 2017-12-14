package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Order;
import project.hms.model.User;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;
import project.hms.repository.UserRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminOrderController(OrderRepository orderRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/order/list";
    }

    @GetMapping("/list")
    @Secured({"CASHIER", "MANAGER"})
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order/list";
    }


    @GetMapping("/info")
    @Secured({"CASHIER", "MANAGER"})
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("order", orderOptional.get());
        return "admin/order/info";
    }

    @PostMapping("/edit/user")
    @Secured({"CASHIER", "MANAGER"})
    public String editUser(@RequestParam(value = "user", required = false) List<Integer> staysId, @RequestParam("orderId") Integer orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new Exception("wrong orderId");
        }
        Order order = orderOptional.get();
        if (staysId != null && staysId.size() > 0) {
            Set<User> set = new HashSet<>();
            for (int stayId : staysId) {
                Optional<User> userOptional = userRepository.findById(stayId);
                if (!userOptional.isPresent()) {
                    throw new Exception("wrong userId");
                }
                set.add(userOptional.get());
            }
            set.add(order.getOwner());
            order.setStays(set);
            orderRepository.save(order);
        }
        return "redirect:/admin/order/edit?id=" + orderId;
    }

    @GetMapping("/edit")
    @Secured({"CASHIER", "MANAGER"})
    public String edit(@RequestParam(value = "id") Integer id,
                       Model model) throws Exception {

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new Exception("wrong id");
        }

        model.addAttribute("order", orderOptional.get());
        return "admin/order/edit";
    }

    @PostMapping("/edit")
    @Secured({"CASHIER", "MANAGER"})
    public String editPOST(@ModelAttribute("order") @Valid Order order, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        final Optional<Order> oldOrderOptional = orderRepository.findById(order.getId());
        if (!oldOrderOptional.isPresent()) {
            throw new Exception("err");
        }
        Order oldOrder = oldOrderOptional.get();

        ModelTool.merge(order, oldOrder);
        order = orderRepository.save(oldOrder);
        return "redirect:/admin/order/info?id=" + order.getId();
    }
}
