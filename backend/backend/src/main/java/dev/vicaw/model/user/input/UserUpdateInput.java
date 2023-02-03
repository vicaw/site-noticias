package dev.vicaw.model.user.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dev.vicaw.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateInput {

    @NotNull(message = "Você deve informar sua senha para realizar modificações")
    @Size(min = 8, max = 128, message = "Verifique sua senha")
    private String currentPassword;

    @Size(min = 8, max = 128, message = "Sua nova senha deve ter entre 8 e 128 caracteres")
    private String newPassword;

    @Size(min = 3, max = 30, message = "Seu novo nome deve ter entre 3 e 30 caracteres")
    private String name;

    private Role role;

}
