package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hms.model.*;
import project.hms.model.enums.GoodsOrderStatus;
import project.hms.model.enums.OrderStatus;
import project.hms.repository.GoodOrderRepository;
import project.hms.repository.GoodRepository;
import project.hms.repository.OrderRepository;
import project.hms.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 订餐的controller
 */
@Controller
@RequestMapping("/good")
public class OrderGoodsController {

    private final GoodRepository goodRepository;
    private final GoodOrderRepository goodOrderRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderGoodsController(GoodRepository goodRepository, GoodOrderRepository goodOrderRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.goodRepository = goodRepository;
        this.goodOrderRepository = goodOrderRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * 返回订餐的界面
     */
    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("goods", goodRepository.findAll());
        return "good/order";
    }

    /**
     * 将订单信息存入数据库
     */
    @PostMapping("/order")
    public String orderPOST(HttpServletRequest req, Principal principal, Model model) throws Exception {

        List<GoodInclude> includes = new LinkedList<>();

        String[] goods = req.getParameterValues("goods");

        for (String i : goods) {
            GoodInclude include = new GoodInclude();

            String[] info = i.split(",");


            int goodId = Integer.parseInt(info[0]);
            int goodAmount = Integer.parseInt(info[1]);

            Optional<Good> goodOptional = goodRepository.findById(goodId);

            if (!goodOptional.isPresent()) {
                throw new Exception("");
            }


            include.setGood(goodOptional.get());
            include.setAmount(goodAmount);

            includes.add(include);
        }
        int sum = 0;
        for (GoodInclude i : includes) {
            i.setCost(i.getGood().getPrice() * i.getAmount());
            sum += i.getGood().getPrice() * i.getAmount();
        }

        GoodOrder goodOrder = new GoodOrder();

        goodOrder.setStatus(GoodsOrderStatus.UNPAID);

        goodOrder.setCost(sum);

        User owner = userRepository.findByUsername(principal.getName());

        goodOrder.setOwner(owner);

        List<Order> orders = orderRepository.findAllByOwner(owner);

        orders.removeIf(gorder -> !gorder.getStatus().equals(OrderStatus.CHECK_IN));

        if (orders.isEmpty()) {
            throw new Exception("您需要先入住酒店才能订餐！");
        }
        goodOrder.setRoom(orders.get(0).getRoom());

        goodOrder.setGincludes(includes);

        GoodOrder savedGoodOrder = goodOrderRepository.save(goodOrder);

        model.addAttribute("unpaidGoodOrder", savedGoodOrder);

        return "good/pay";
    }

    /**
     * 确认支付，将订单信息修改为已支付
     *
     * @param id 订单id
     */
    @GetMapping("/confirmPay")
    public String pay(@RequestParam(value = "id") Integer id) throws Exception {
        Optional<GoodOrder> GoodOrderOptional = goodOrderRepository.findById(id);
        if (!GoodOrderOptional.isPresent()) {
            throw new Exception("invalid order");
        }

        GoodOrder goodOrder = GoodOrderOptional.get();
        goodOrder.setStatus(GoodsOrderStatus.PAID);
        goodOrderRepository.save(goodOrder);

        return "good/paid";

    }

    /**
     * 返回主页
     */
    @GetMapping("/paid")
    public String paid() {
        return "redirect:/";
    }

}
