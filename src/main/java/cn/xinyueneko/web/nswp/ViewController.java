package cn.xinyueneko.web.nswp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("p1", "此页面暂未完善");
        return "index";
    }

}
