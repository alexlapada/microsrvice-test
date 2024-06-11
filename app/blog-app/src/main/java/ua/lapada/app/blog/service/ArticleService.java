package ua.lapada.app.blog.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lapada.app.blog.mapper.entity.ArticleEntityMapper;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.app.blog.persistance.repository.ArticleRepository;
import ua.lapada.app.blog.service.model.ArticleCreateModel;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleEntityMapper articleEntityMapper;

    @Transactional
    public Article create(ArticleCreateModel model, String userId) {
        Article article = articleEntityMapper.map(model);
        article.setCreatedBy(userId);
        repository.saveAndFlush(article);
        log.info("New article created successfully. {}", article);
        return article;
    }

    @Transactional
    public Article update(String id, ArticleCreateModel model) {
        Article article = getById(id);
        articleEntityMapper.fill(model, article);
        repository.saveAndFlush(article);
        log.info("Article with id {} updated successfully. {}", id, article);
        return article;
    }

    @Transactional
    public void delete(String id) {
        Article article = getById(id);
        repository.delete(article);
        log.info("Article with id {} deleted successfully.", id);
    }

    @Transactional(readOnly = true)
    public Page<Article> getAllBeforeDate(LocalDate date, Pageable pageable) {
        Instant instant = date.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
        return repository.findAllByCreatedAtBefore(instant, pageable);
    }

    @Transactional(readOnly = true)
    public Article getById(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Article with id %s not found.", id)));
    }
}
