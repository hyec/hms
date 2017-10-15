package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.data.dto.OrderDto;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;
import project.hms.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(UserRepository userRepository,
                           RoomRepository roomRepository,
                           OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/new")
    public String newGET() {
        return "order/";
    }

    @PostMapping("/new")
    public String newPOST(@Valid OrderDto orderDto, BindingResult result,
                          Principal principal) throws Exception {

        if (result.hasErrors()) {
            throw new Exception("Error");
        }


        return "redirect:/order/pay";
    }

}
