package dev.vicaw.model.category.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryInput {
    @NotNull(message = "O nome da categoria não pode ficar em branco")
    @Size(min = 3, max = 200, message = "O nome da categoria deve ter entre 3 e 200 caracteres")
    String name;
}
