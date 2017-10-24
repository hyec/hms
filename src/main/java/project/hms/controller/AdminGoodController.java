package project.hms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Good;
import project.hms.repository.GoodRepository;
import project.hms.util.ModelTool;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/good")
public class AdminGoodController {

    private final GoodRepository repository;

    @Autowired
    public AdminGoodController(GoodRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/admin/good/list";
    }

    @GetMapping("/list")
    @Secured({"CASHIER", "MANAGER"})
    public String list(Model model) {
        model.addAttribute("goods", repository.findAll());
        return "good/list";
    }

    @GetMapping("/info")
    @Secured({"CASHIER", "MANAGER"})
    public String info(@RequestParam("id") Integer id, Model model) throws Exception {

        Optional<Good> goodOptional = repository.findById(id);
        if (!goodOptional.isPresent()) {
            throw new Exception("error");
        }

        model.addAttribute("goods", goodOptional.get());
        return "good/info";

    }

    @GetMapping("/edit")
    @Secured({"MANAGER"})
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<Good> goodOptional = repository.findById(id);
            if (!goodOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("goods", goodOptional.get());
        } else {
            model.addAttribute("goods", new Good());
        }

        return "good/edit";
    }

    @PostMapping("/edit")
    @Secured({"MANAGER"})
    public String editPOST(@ModelAttribute("goods") @Valid Good good, BindingResult result) {

        if (result.hasErrors()) {
            return "good/edit";
        }

        Good savedGood;

        Optional<Good> goodOptional = repository.findById(good.getId());
        if (goodOptional.isPresent()) {
            savedGood = goodOptional.get();
            ModelTool.merge(good, savedGood);
            good = savedGood;
        }

        savedGood = repository.save(good);

        return "redirect:/admin/good/info?id=" + savedGood.getId();
    }
}
