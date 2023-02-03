package dev.vicaw.model.article;

import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.input.ArticleUpdateInput;
import dev.vicaw.model.article.output.ArticleOutput;
import dev.vicaw.model.user.UserMapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    default ArticleOutput toArticleOutput(Article noticia) {
        return ArticleOutput
                .builder()
                .id(noticia.getId())
                .author(userMapper.toUserProfileOutput(noticia.getAuthor()))
                .titulo(noticia.getTitulo())
                .subtitulo(noticia.getSubtitulo())
                .body(noticia.getBody())
                .createdAt(noticia.getCreatedAt())
                .updatedAt(noticia.getUpdatedAt())
                .coverImgName(noticia.getCoverImgName())
                .category(noticia.getCategory())

                .build();
    }

    Article toModel(ArticleInput articleInput);

    void updateEntityFromInput(ArticleInput input, @MappingTarget Article entity);

    void updateEntityFromInput(ArticleUpdateInput input, @MappingTarget Article entity);

}