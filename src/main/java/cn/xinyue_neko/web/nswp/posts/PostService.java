package cn.xinyue_neko.web.nswp.posts;

import cn.xinyue_neko.web.nswp.NswpApplication;
import cn.xinyue_neko.web.nswp.utils.AppPathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    private final Path postsDir;

    public PostService(@Value("${nsapp.posts.dir:posts}") String dir) {
        postsDir = AppPathUtils.resolvePostsDir(dir, NswpApplication.class);
    }

    public List<PostMeta> lsPosts() throws IOException {
        Path jsonPath = postsDir.resolve("list.jsonc");

        if (!Files.isRegularFile(jsonPath)) {
            return new ArrayList<>();
        }

        JsonMapper mapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS)
                .enable(JsonReadFeature.ALLOW_YAML_COMMENTS)
                .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
                .enable(JsonReadFeature.ALLOW_TRAILING_COMMA)
                .build();

        TypeReference<List<PostMeta>> typeRef = new TypeReference<>() {};

        return mapper.readValue(jsonPath.toFile(), typeRef);
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
