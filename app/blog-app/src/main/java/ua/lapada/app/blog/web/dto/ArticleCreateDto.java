package ua.lapada.app.blog.web.dto;

import lombok.Data;

@Data
public class ArticleCreateDto {
    private String title;
    private String payload;
}
