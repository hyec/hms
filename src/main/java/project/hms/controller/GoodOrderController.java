package project.hms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hms.model.GoodOrder;
import project.hms.model.enums.GoodsOrderStatus;
import project.hms.repository.GoodOrderRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("/info")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<GoodOrder> goodOrderOptional = goodOrderRepository.findById(id);
        if (!goodOrderOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("goodOrder", goodOrderOptional.get());
        return "admin/gorders/info";
    }

    @GetMapping("/edit")
    @Secured({"ROLE_MANAGER"})
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<GoodOrder> goodOrderOptional = goodOrderRepository.findById(id);
            if (!goodOrderOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("goodOrder", goodOrderOptional.get());
        } else {
            model.addAttribute("goodOrder", new GoodOrder());
        }

        return "admin/gorders/edit";
    }

    @PostMapping("/edit")
    @Secured({"ROLE_MANAGER"})
    public String editPOST(@Valid GoodOrder goodOrder, BindingResult result) {

        if (result.hasErrors()) {
            return "gorders/edit";
        }

        GoodOrder savedGorders;
        Optional<GoodOrder> goodOrderOptional = goodOrderRepository.findById(goodOrder.getId());
        if (goodOrderOptional.isPresent()) {
            savedGorders = goodOrderOptional.get();
            ModelTool.merge(goodOrder, savedGorders);
            goodOrder = savedGorders;
        } else {
            goodOrder.setStatus(GoodsOrderStatus.UNPAID);
        }

        savedGorders = goodOrderRepository.save(goodOrder);

        return "redirect:/admin/gorders/info?id=" + savedGorders.getId();
    }
}
