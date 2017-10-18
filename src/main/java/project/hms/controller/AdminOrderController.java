package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Order;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    // private final RoomRepository roomRepository;

    @Autowired
    public AdminOrderController(OrderRepository orderRepository, RoomRepository roomRepository) {
        this.orderRepository = orderRepository;
        //this.roomRepository = roomRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "order/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("order", orderOptional.get());
        return "order/info";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Integer id,
                       Model model) throws Exception {

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("order", orderOptional.get());
        //model.addAttribute("emptyRooms", roomRepository.findAllByStatus(RoomStatus.EMPTY));
        return "order/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@ModelAttribute("order") @Valid Order order, BindResult result) {

        Order savaOrder = orderRepository.save(order);
        return "redirect:/admin/order/info?id=" + savaOrder.getId();
    }
}
