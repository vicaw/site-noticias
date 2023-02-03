package dev.vicaw.model.article.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedOutput {
    boolean hasMore;
    List<ArticleFeedOutput> articles;
}
