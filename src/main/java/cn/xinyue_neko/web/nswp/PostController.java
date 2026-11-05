package cn.xinyue_neko.web.nswp;

import cn.xinyue_neko.web.nswp.posts.PostMeta;
import cn.xinyue_neko.web.nswp.posts.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Deprecated
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService postService) {
        service = postService;
    }

    @GetMapping
    public List<PostMeta> list() throws IOException {
        return service.lsPosts();
    }

    @GetMapping(value = "/{name}", produces = MediaType.TEXT_HTML_VALUE)
    public String read(@PathVariable String name) throws IOException {
        return service.readPost(name);
    }
}
