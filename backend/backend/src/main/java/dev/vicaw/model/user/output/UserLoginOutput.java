package dev.vicaw.model.user.output;

import dev.vicaw.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginOutput {
    String token;
    User user;
}
