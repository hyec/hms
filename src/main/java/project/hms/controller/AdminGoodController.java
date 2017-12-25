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

/**
 * 管理员管理商品的controller
 */
@Controller
@RequestMapping("/admin/good")
public class AdminGoodController {

    private final GoodRepository repository;

    @Autowired
    public AdminGoodController(GoodRepository repository) {
        this.repository = repository;
    }

    /**
     * @return 返回商品列表的页面
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/good/list";
    }

    /**
     * 进入商品列表页面
     *
     * @return 商品列表
     */
    @GetMapping("/list")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String list(Model model) {
        model.addAttribute("goods", repository.findAll());
        return "admin/good/list";
    }

    /**
     * 输入id获得对应id的商品信息
     * @param id 商品id
     * @return 对应商品信息
     * @throws Exception 商品不存在
     */
    @GetMapping("/info")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String info(@RequestParam("id") Integer id, Model model) throws Exception {

        Optional<Good> goodOptional = repository.findById(id);
        if (!goodOptional.isPresent()) {
            throw new Exception("error");
        }

        model.addAttribute("goods", goodOptional.get());
        return "admin/good/info";

    }

    /**
     * 编辑商品信息
     * @param id 商品id
     * @return 编辑商品信息界面
     * @throws Exception 商品不存在
     */
    @GetMapping("/edit")
    @Secured({"ROLE_MANAGER"})
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

        return "admin/good/edit";
    }

    /**
     * 将修改后的商品信息替换原数据库中信息
     * @param good 修改后的商品信息
     */
    @PostMapping("/edit")
    @Secured({"ROLE_MANAGER"})
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
