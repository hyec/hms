package project.hms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hms.repository.GoodOrderRepository;

@Controller
@RequestMapping("/admin/gorders")
public class GoodOrderController {
    private final GoodOrderRepository goodOrderRepository;

    public GoodOrderController(GoodOrderRepository goodOrderRepository) {
        this.goodOrderRepository = goodOrderRepository;
    }

    @GetMapping("/list")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String list(Model model) {
        model.addAttribute("goodOrder", goodOrderRepository.findAll());
        return "admin/gorders/list";
    }
}
