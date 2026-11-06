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
        var p1 = """
这是一个1.13.2的Spigot服务器
猫之夏专注于休闲、探索、怀旧等玩法
                """;

        model.addAttribute("p1", p1);
        return "index";
    }

    @GetMapping({"/blogs"})
    public String blogs(Model model) throws IOException {
        var posts = service.lsPosts();
        model.addAttribute("p1", "这边是博客的列表");
        model.addAttribute("posts", posts);

        return "blogs";
    }

    @GetMapping({"/about"})
    public String about(Model model) {
        var text = """
我们只是一个人, 一个高中生为爱发电做出来的服务器
                """;
        model.addAttribute("text", text);
        return "about";
    }

}
