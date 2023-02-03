package dev.vicaw.model.comment.output;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.vicaw.model.user.output.UserProfileOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommentOutput {

    private Long id;
    private Long articleId;
    private Long parentId;
    private UserProfileOutput author;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentOutput> children;

}
