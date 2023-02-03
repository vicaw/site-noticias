package dev.vicaw.model.comment.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentInput {
    @NotNull(message = "O comentário precisa estar vinculado à uma notícia")
    private Long articleId;

    @NotNull(message = "O comentário precisa estar vinculado à um usuário")
    private Long authorId;

    private Long parentId;

    @NotNull(message = "O comentário não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O comentário deve ter entre 3 e 280 caracteres")
    private String body;
}
