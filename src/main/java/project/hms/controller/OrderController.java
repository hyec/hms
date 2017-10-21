package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.data.dto.OrderDto;
import project.hms.model.Order;
import project.hms.model.Room;
import project.hms.model.User;
import project.hms.model.enums.OrderStatus;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;
import project.hms.repository.UserRepository;
import project.hms.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final OrderRepository orderRepository;

    private final OrderService orderService;
    @Autowired
    public OrderController(UserRepository userRepository,
                           RoomRepository roomRepository,
                           OrderRepository orderRepository,
                           OrderService orderService) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/new")
    public String newGET(OrderDto orderDto) {
        return "order/newOrder";
    }


    @PostMapping("/new")
    public String newPOST(@Valid OrderDto orderDto, BindingResult result,
                          Principal principal, Model model) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        User user = null;
        try {
            user = userRepository.findByUsername(principal.getName());
        } catch (NullPointerException e) {
            System.out.println("Î´µÇÂ¼");
            return "redirect:/user/login";
        }
        /*if (userOptional.isPresent()) {
            result.rejectValue("username", "Username already exists.");
            return "register";
        }*/

        List<Room> availableRooms = orderService.getAvailableRooms(orderDto.getRoomType(), orderDto.getCheckInTime(), orderDto.getCheckOutTime());
        if (availableRooms.isEmpty()) {
            return "order/noroom";
        }
        Order order = new Order();
        Room room = availableRooms.get(0);
        room.setStatus(RoomStatus.OCCUPIED);
        order.setRoom(room);
        order.setStatus(OrderStatus.UNPAID);
        order.setCheckInTime(orderDto.getCheckInTime());
        order.setCheckOutTime(orderDto.getCheckOutTime());
        order.setOwner(user);
        order.setPrice(room.getPrice() * (orderDto.getCheckOutTime().getTime() - orderDto.getCheckInTime().getTime()) / (1000 * 3600 * 24));
        Order save = orderRepository.save(order);
        model.addAttribute("unpaidOrder", save);
        return "order/pay";
    }

    @PostMapping("/searchroom")
    @ResponseBody
    public String searchRoom(@Valid OrderDto orderDto, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        List<Room> availableRooms = orderService.getAvailableRooms(orderDto.getRoomType(), orderDto.getCheckInTime(), orderDto.getCheckOutTime());
        if (availableRooms.isEmpty()) {
            return "-1";
        }
        return availableRooms.size() + "";
    }

    @GetMapping("/confirmPay")
    public String confirmPay(@RequestParam(value = "id") int id) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new Exception("invilid order");
        }
        Order order = orderOptional.get();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        return "redirect:/";
    }
}
