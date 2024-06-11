package ua.lapada.app.blog.web.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.lapada.app.blog.mapper.web.ArticleDtoMapper;
import ua.lapada.app.blog.security.AuthorizedUserContext;
import ua.lapada.app.blog.service.ArticleService;
import ua.lapada.app.blog.web.dto.ArticleModifyDto;
import ua.lapada.app.blog.web.dto.ArticleDto;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(ArticleController.ENDPOINT)
@AllArgsConstructor
public class ArticleController {
    static final String ENDPOINT = "/articles";
    static final String BY_ID_ENDPOINT = "/{id}";
    static final String ALL_BEFORE_DATE_ENDPOINT = "/before-date";

    private final ArticleService articleService;
    private final ArticleDtoMapper articleDtoMapper;

    @GetMapping(BY_ID_ENDPOINT)
    public ArticleDto get(@PathVariable String id) {
        return articleDtoMapper.map(articleService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDto create(@Valid @RequestBody ArticleModifyDto dto) {
        return articleDtoMapper.map(articleService.create(articleDtoMapper.map(dto), AuthorizedUserContext.get().getUserId()));
    }

    @PutMapping(BY_ID_ENDPOINT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ArticleDto update(@PathVariable String id, @Valid @RequestBody ArticleModifyDto dto) {
        return articleDtoMapper.map(articleService.update(id, articleDtoMapper.map(dto)));
    }

    @DeleteMapping(BY_ID_ENDPOINT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        articleService.delete(id);
    }

    @GetMapping(ALL_BEFORE_DATE_ENDPOINT)
    @PreAuthorize("hasAuthority(T(ua.lapada.app.blog.persistance.entity.Role).ADMIN)")
    public Page<ArticleDto> getAllBeforeDate(@RequestParam String date, @PageableDefault Pageable pageable) {
        return articleService.getAllBeforeDate(LocalDate.parse(date), pageable).map(articleDtoMapper::map);
    }

}
