package ua.lapada.app.blog.web.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.app.blog.service.ArticleService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ArticleControllerTest extends AbstractControllerTest {
    @Autowired
    private ArticleService service;

    @Test
    public void getById_Success() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = getArticle(id);
        when(service.getById(id)).thenReturn(article);

        mockMvc.perform(get(ArticleController.ENDPOINT + ArticleController.BY_ID_ENDPOINT, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(article.getId())));

        verify(service, only()).getById(id);

    }

    @Test
    public void testConcurrent() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = getArticle(id);
        when(service.getById(id)).thenReturn(article);

        mockMvc.perform(get(ArticleController.ENDPOINT + "/event-test/{test}", id))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(article.getId())));

        verify(service, only()).getById(id);

    }

    @Test
    public void getById_NotFountException() throws Exception {
        String id = UUID.randomUUID().toString();
        doThrow(EntityNotFoundException.class).when(service).getById(id);

        mockMvc.perform(get(ArticleController.ENDPOINT + ArticleController.BY_ID_ENDPOINT, id))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message", is("Entity was not found")));

        verify(service, only()).getById(id);

    }

    @Test
    void getAllBeforeDate() throws Exception {
        // Given
        String date = "2024-09-01";
        LocalDate localDate = LocalDate.parse(date);
        String id = UUID.randomUUID().toString();
        Article article = getArticle(id);
        Pageable pageable = PageRequest.of(0, 10);
        final List<Article> articles = singletonList(article);
        final Page<Article> page = new PageImpl<>(articles, pageable, articles.size());
        when(service.getAllBeforeDate(localDate, pageable)).thenReturn(page);

        // When
        ResultActions result = mockMvc.perform(get(ArticleController.ENDPOINT + ArticleController.ALL_BEFORE_DATE_ENDPOINT)
                                .param(PAGE_NUMBER_PARAM_NAME, String.valueOf(pageable.getPageNumber()))
                                .param(PAGE_SIZE_PARAM_NAME, String.valueOf(pageable.getPageSize()))
                                .param("date", date));

        // Then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.content", Matchers.hasSize(articles.size())))
              .andExpect(jsonPath("$.content[0].id", is(id)));
        verify(service, only()).getAllBeforeDate(localDate, pageable);
    }

    private Article getArticle(String id) {
        return Article.builder()
                      .id(id)
                      .build();
    }
}
