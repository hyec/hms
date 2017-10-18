package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.repository.GoodRepository;

@Controller
@RequestMapping("/admin/good")
public class OrderGoodsController {

    private final GoodRepository repository;

    @Autowired
    public OrderGoodsController(GoodRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("goods", repository.findAll());
        return "good/order";
    }

    @PostMapping("order")
    public String orderPost() {

        return "good/order";
    }

}
