package ua.lapada.app.blog.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.lapada.app.blog.persistance.entity.Article;

import java.time.Instant;

public interface ArticleRepository extends JpaRepository<Article, String> {

    Page<Article> findAllByCreatedAtBefore(Instant date, Pageable pageable);
}
