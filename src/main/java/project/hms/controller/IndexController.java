package project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index(Model m) {
        m.addAttribute("title", "HMS");
        return "index";
    }

    @GetMapping("/example")
    public String example() {
        return "example";
    }

}
