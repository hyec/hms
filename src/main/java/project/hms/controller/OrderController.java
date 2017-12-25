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
import project.hms.repository.UserRepository;
import project.hms.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * 用户订单界面系统的controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderController(UserRepository userRepository,
                           OrderRepository orderRepository,
                           OrderService orderService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    /**
     * 返回get请求创建订单的页面
     */
    @GetMapping("/new")
    public String newGET(@ModelAttribute OrderDto orderDto) {
        return "order/newOrder";
    }

    /**
     * 通过定义的orderDto,接受用户填写的创建订单所需要的参数
     * 判断是否有符合住户要求的房间，若有则生成订单并跳转到支付页面，否则跳转到订房失败页面
     */
    @PostMapping("/new")
    public String newPOST(@Valid OrderDto orderDto, BindingResult result,
                          Principal principal, Model model) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        User user;
        try {
            user = userRepository.findByUsername(principal.getName());
        } catch (NullPointerException e) {
            return "redirect:/user/login";
        }


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

    /**
     * 用于选房时ajax判断是否有符合条件的房间
     *
     * @return 返回符合条件的房间数量，-1代表没有可用房间
     */
    @PostMapping("/searchroom")
    @ResponseBody
    public String searchRoom(@Valid OrderDto orderDto, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception("Error");
        }
        List<Room> availableRooms = orderService.getAvailableRooms(orderDto.getRoomType(), orderDto.getCheckInTime(), orderDto.getCheckOutTime());
        if (availableRooms.isEmpty()) {
            return "-1";
        }
        return availableRooms.size() + "";
    }

    /**
     * 当用户支付订单后，将订单的状态修改为已支付
     */
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
