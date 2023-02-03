package dev.vicaw.model.article.output;

import java.time.LocalDateTime;

import dev.vicaw.model.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleFeedOutput {
    private Long id;
    private String slug;
    private String coverImgName;
    private String chapeuFeed;
    private String tituloFeed;
    private String resumoFeed;
    private LocalDateTime createdAt;
    private Category category;
}
