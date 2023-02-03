package dev.vicaw.model.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserProfileOutput;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserProfileOutput toUserProfileOutput(User user);

    User toModel(UserCreateInput input);

    UserLoginInput toLoginInput(User user);

    UserLoginInput toLoginInput(UserCreateInput createInput);

}