package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.model.Good;
import project.hms.model.GoodInclude;
import project.hms.model.GoodOrder;
import project.hms.repository.GoodOrderRepository;
import project.hms.repository.GoodRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/good")
public class OrderGoodsController {

    private final GoodRepository goodRepository;
    private final GoodOrderRepository goodOrderRepository;

    @Autowired
    public OrderGoodsController(GoodRepository goodRepository, GoodOrderRepository goodOrderRepository) {
        this.goodRepository = goodRepository;
        this.goodOrderRepository = goodOrderRepository;
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("goods", goodRepository.findAll());
        return "good/order";
    }

    @PostMapping("/order")
    public String orderPOST(HttpServletRequest req) throws Exception {

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

        includes.forEach(i -> i.setCost(i.getGood().getPrice() * i.getAmount()));

        GoodOrder goodOrder = new GoodOrder();

        goodOrder.setGincludes(includes);

        goodOrderRepository.save(goodOrder);

        return "good/order";
    }

}
