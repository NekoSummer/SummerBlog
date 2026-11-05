package cn.xinyueneko.web.nswp.posts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Slf4j
public class PostsDirInitializer implements ApplicationRunner {

    private final Path postsDir;

    public PostsDirInitializer(@Value("${spring.posts.dir:posts}") String dir) {
        postsDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            Files.createDirectories(postsDir);
            log.info("posts is Created: {}", postsDir);

            try (Stream<Path> stream = Files.list(postsDir)) {
                stream.filter(p -> p.toString().toLowerCase().endsWith(".html"))
                        .forEach(p -> log.info("发现文章: {}", p.getFileName()));
            }
        } catch (IOException e) {
            log.error("初始化 posts 目录失败: {}", postsDir, e);
            throw e;
        }
    }
}
