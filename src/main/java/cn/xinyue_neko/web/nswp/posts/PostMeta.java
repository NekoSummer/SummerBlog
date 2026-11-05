package cn.xinyue_neko.web.nswp.posts;

import lombok.Getter;

@Getter
public class PostMeta {
    private String filename;
    private String title;

    // Getters 和 Setters（如果你用了 Lombok，直接加个 @Data 注解即可）
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
