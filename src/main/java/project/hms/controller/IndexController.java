package project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前台首页控制器
 */
@Controller
public class IndexController {

    /**
     * 首页，返回首页模板
     */
    @GetMapping({"", "/"})
    public String index(Model m) {
        m.addAttribute("title", "HMS");
        return "index";
    }

}
