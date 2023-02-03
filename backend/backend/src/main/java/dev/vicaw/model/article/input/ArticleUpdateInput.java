package dev.vicaw.model.article.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// ID do autor removido para evitar alterações.
public class ArticleUpdateInput {
    @NotNull(message = "O título não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O título deve ter entre 3 e 280 caracteres")
    private String titulo;

    @NotNull(message = "O subtítulo não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O subtítulo deve ter entre 3 e 280 caracteres")
    private String subtitulo;

    @NotNull(message = "O corpo da notícia não pode ficar em branco")
    @Size(min = 3, message = "O corpo da notícia é muito curto.")
    private String body;

    @NotNull(message = "O chapéu (Feed) não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O chapéu (Feed) deve ter entre 3 e 280 caracteres")
    private String chapeuFeed;

    @NotNull(message = "O título (Feed) não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O título (Feed) deve ter entre 3 e 280 caracteres")
    private String tituloFeed;

    @NotNull(message = "O resumo (Feed) não pode ficar em branco")
    @Size(min = 3, max = 280, message = "O resumo (Feed) deve ter entre 3 e 280 caracteres")
    private String resumoFeed;

    @NotNull(message = "A notícia precisa estar vinculada à uma categoria.")
    private Long categoryId;
}
