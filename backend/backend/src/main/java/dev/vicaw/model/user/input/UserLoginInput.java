package dev.vicaw.model.user.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginInput {
    @Email(message = "E-mail mal formatado")
    @NotNull(message = "Seu e-email não pode ficar em branco")
    private String email;

    @NotNull(message = "Sua senha não pode ficar em branco")
    @Size(min = 8, max = 128, message = "Sua senha deve ter entre 8 e 128 caracteres")
    private String password;
}
