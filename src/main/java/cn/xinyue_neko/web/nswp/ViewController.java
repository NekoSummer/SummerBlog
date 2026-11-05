package cn.xinyue_neko.web.nswp;

import cn.xinyue_neko.web.nswp.posts.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ViewController {

    private final PostService service;

    public ViewController(PostService postService) {
        service = postService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("p1", "此页面暂未完善");
        return "index";
    }

    @GetMapping({"/blogs"})
    public String blogs(Model model) throws IOException {
        var posts = service.lsPosts();
        model.addAttribute("p1", "这边是博客的列表");
        model.addAttribute("posts", posts);

        return "blogs";
    }

}
