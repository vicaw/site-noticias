package dev.vicaw.model.comment.output;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentsOutput {
    boolean hasMore;
    List<CommentOutput> comments;
}
