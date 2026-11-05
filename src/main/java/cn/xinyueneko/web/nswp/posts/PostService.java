package cn.xinyueneko.web.nswp.posts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {
    private final Path postsDir;

    public PostService(@Value("${nsapp.posts.dir:posts}") String dir) {
        postsDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    public List<String> lsPosts() throws IOException {
        try (Stream<Path> stream = Files.list(postsDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".html"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public String readPost(String filename) throws IOException {
        Path target = postsDir.resolve(filename).normalize();

        if (!target.startsWith(postsDir)) {
            throw new IllegalArgumentException("非法路径: " + filename);
        }
        if (!Files.isRegularFile(target)) {
            throw new NoSuchFileException(filename);
        }
        return Files.readString(target, StandardCharsets.UTF_8);
    }
}
