package cn.xinyue_neko.web.nswp.conf;

import cn.xinyue_neko.web.nswp.NswpApplication;
import cn.xinyue_neko.web.nswp.utils.AppPathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${nsapp.posts.dir:posts}")
    private String postsDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = AppPathUtils.resolvePostsDir(postsDir, NswpApplication.class);
        String location = path.toUri().toString();
        if (!location.endsWith("/")) location += "/";

        registry.addResourceHandler("/posts/**")
                .addResourceLocations(location);
    }
}
