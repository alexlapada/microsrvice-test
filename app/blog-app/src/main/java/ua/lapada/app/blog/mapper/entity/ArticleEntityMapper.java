package ua.lapada.app.blog.mapper.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ua.lapada.app.blog.persistance.entity.Article;
import ua.lapada.app.blog.service.model.ArticleCreateModel;

@Mapper(componentModel = ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleEntityMapper {

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "createdAt", ignore = true)
  })
  Article map(ArticleCreateModel model);

  @Mappings({
          @Mapping(target = "id", ignore = true),
          @Mapping(target = "createdAt", ignore = true)
  })
  void fill(ArticleCreateModel model, @MappingTarget Article article);

}
