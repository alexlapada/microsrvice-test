package ua.lapada.app.blog.web.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ArticleDto {
    private String id;
    private String title;
    private String payload;
    private Instant createdAt;
}
