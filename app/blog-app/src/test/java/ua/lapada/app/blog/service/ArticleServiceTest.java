package ua.lapada.app.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.lapada.app.blog.constant.AppConstant;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.app.blog.service.model.ArticleCreateModel;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest extends AbstractServiceTest {
    @Autowired
    ArticleService service;

    @Test
    public void create() {
        // Given
        String payload = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        String userId = AppConstant.ADMIN_USER_ID;
        ArticleCreateModel model = new ArticleCreateModel();
        model.setPayload(payload);
        model.setTitle(title);

        // When
        Article created = service.create(model, userId);

        // Then
        assertThat(created)
                .isNotNull()
                .hasFieldOrProperty(Article.Fields.id)
                .hasFieldOrPropertyWithValue(Article.Fields.title, title)
                .hasFieldOrPropertyWithValue(Article.Fields.payload, payload)
                .hasFieldOrProperty(Article.Fields.createdAt)
                .hasFieldOrPropertyWithValue(Article.Fields.createdBy, userId);
    }

    @Test
    @Sql(scripts = {Dmls.ADD_ARTICLES})
    public void getAllBeforeDate_Success() {
        // Given
        LocalDate date = LocalDate.of(2024, 6, 9);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Article> response = service.getAllBeforeDate(date, pageable);

        // Then
        assertThat(response).hasSize(2).map(Article::getId).containsExactlyInAnyOrder("-2", "-4");
    }
}
