package cn.xinyue_neko.web.nswp.utils;

import org.springframework.boot.system.ApplicationHome;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppPathUtils {

    public static Path getBaseDir(Class<?> mainClass) {
        ApplicationHome home = new ApplicationHome(mainClass);
        return home.getDir().toPath();
    }

    public static Path resolvePostsDir(String configPath, Class<?> mainClass) {
        Path path = Paths.get(configPath);
        if (path.isAbsolute()) {
            return path.normalize();
        }
        return getBaseDir(mainClass).resolve(path).normalize();
    }
}
