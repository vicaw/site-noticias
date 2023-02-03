package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.user.User;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.input.UserUpdateInput;
import dev.vicaw.model.user.output.UserLoginOutput;
import dev.vicaw.model.user.output.UserProfileOutput;

public interface UserService {

    public List<User> list();

    public User getById(Long id);

    public UserProfileOutput getUserProfileById(Long id);

    public UserLoginOutput create(UserCreateInput userInput);

    public UserLoginOutput login(UserLoginInput loginInput);

    public UserProfileOutput update(Long userId, UserUpdateInput updateInput);

    public void delete(Long id);

}
