package ua.lapada.app.blog.mapper.web;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.app.blog.service.model.ArticleCreateModel;
import ua.lapada.app.blog.web.dto.ArticleCreateDto;
import ua.lapada.app.blog.web.dto.ArticleDto;

@Mapper(componentModel = ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleDtoMapper {

  ArticleCreateModel map(ArticleCreateDto dto);

  ArticleDto map(Article entity);
}
