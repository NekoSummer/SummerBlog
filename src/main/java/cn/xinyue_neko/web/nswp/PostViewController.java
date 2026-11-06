package cn.xinyue_neko.web.nswp;

import cn.xinyue_neko.web.nswp.utils.AppPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * text: 这是一个加载器
 * 用于加载posts/目录的html文件
 *
 * @author NewmoomCat
 */

@Slf4j
@Controller
public class PostViewController {

    private final Path postsDir;
    private final SpringTemplateEngine templateEngine;

    public PostViewController(@Value("${nsapp.posts.dir:posts}") String dir) {
        this.postsDir = AppPathUtils.resolvePostsDir(dir, NswpApplication.class);

        // 依然保留专门处理字符串的引擎和解析器
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        this.templateEngine = new SpringTemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

    @GetMapping("/posts/{name:.+}")
    public ResponseEntity<String> renderPost(
            @PathVariable String name,
            HttpServletRequest request,
            HttpServletResponse response) { // 接收 request 和 response 用于构建 Web 环境

        Path target = postsDir.resolve(name).normalize();

        if (!target.startsWith(postsDir) || !Files.isRegularFile(target)) {
            return ResponseEntity.notFound().build();
        }

        try {
            String htmlContent = Files.readString(target, StandardCharsets.UTF_8);

            // 【关键修复】构建带有 Web 环境的 IWebContext
            JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(request.getServletContext());
            IWebExchange webExchange = application.buildExchange(request, response);
            WebContext context = new WebContext(webExchange, request.getLocale());

            // 这样解析 @{/images/logo.svg} 时，Thymeleaf 就能获取到正确的网站根目录了
            String renderedHtml = templateEngine.process(htmlContent, context);

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(renderedHtml);

        } catch (IOException e) {
            log.error("读取或渲染文章失败，请求路径: {}", target, e);
            return ResponseEntity.internalServerError().body("读取文章失败");
        }
    }
}