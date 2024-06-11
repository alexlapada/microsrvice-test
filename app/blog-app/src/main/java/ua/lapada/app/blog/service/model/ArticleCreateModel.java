package ua.lapada.app.blog.service.model;

import lombok.Data;

@Data
public class ArticleCreateModel {
    private String title;
    private String payload;
}
