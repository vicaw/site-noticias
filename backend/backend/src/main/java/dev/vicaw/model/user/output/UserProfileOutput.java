package dev.vicaw.model.user.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.vicaw.model.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UserProfileOutput {

    private Long id;
    private Role role;
    private String name;

}