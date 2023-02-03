package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.Claims;

import dev.vicaw.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.smallrye.jwt.build.Jwt;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.user.Role;
import dev.vicaw.model.user.User;
import dev.vicaw.model.user.UserMapper;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.input.UserUpdateInput;
import dev.vicaw.model.user.output.UserLoginOutput;
import dev.vicaw.model.user.output.UserProfileOutput;
import dev.vicaw.repository.UserRepository;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    JsonWebToken token;

    @Override
    public List<User> list() {
        return userRepository.listAll();
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findByIdOptional(id);

        if (user.isEmpty())
            throw new ApiException(404, "Não foi encontrado nenhum usuário com o ID informado.");

        return user.get();
    }

    @Override
    public UserProfileOutput getUserProfileById(Long id) {
        return userMapper.toUserProfileOutput(getById(id));
    }

    @Transactional
    @Override
    public UserLoginOutput create(UserCreateInput userInput) {
        User user = userMapper.toModel(userInput);

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new ApiException(409, "O e-mail informado já está cadastrado.");

        user.setRole(Role.USER);
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));

        userRepository.persist(user);

        return login(userMapper.toLoginInput(userInput));
    }

    @Override
    public UserLoginOutput login(UserLoginInput loginInput) {
        Optional<User> entity = userRepository.findByEmail(loginInput.getEmail());

        if (entity.isEmpty())
            throw new ApiException(401, "Seu usuário ou senha estão incorretos.");

        User user = entity.get();

        if (!BcryptUtil.matches(loginInput.getPassword(), user.getPassword()))
            throw new ApiException(401, "Seu usuário ou senha estão incorretos.");

        String token = Jwt
                .issuer("http://localhost:8080")
                .upn(user.getEmail())
                .groups(user.getRole().toString())
                .claim(Claims.full_name, user.getName())
                .claim(Claims.sub, user.getId().toString())
                .expiresIn(60 * 60 * 7)
                .sign();

        UserLoginOutput userOutput = new UserLoginOutput(token, user);

        return userOutput;

    }

    @Transactional
    @Override
    public UserProfileOutput update(Long userId, UserUpdateInput updateInput) {
        User user = getById(userId);

        // Pega o ID no token.
        String subject = token.getSubject();

        // Se for diferente, é ADMIN.
        if (!subject.equals(user.getId().toString())) {

            // Não é permitido atualizar dados de outros administradores.
            if (user.getRole() == Role.ADMIN)
                throw new ApiException(403, "Não é permitido modificar dados de administradores.");

            User adminUser = getById(Long.valueOf(subject));

            // Verifica a senha informada
            if (!BcryptUtil.matches(updateInput.getCurrentPassword(), adminUser.getPassword()))
                throw new ApiException(401, "Sua senha está incorreta.");

            if (updateInput.getName() != null)
                user.setName(updateInput.getName());

            if (updateInput.getRole() != null) {

                // Não é permitido criar administradores.
                if (updateInput.getRole() == Role.ADMIN)
                    throw new ApiException(403, "Não é permitido elevar outros usuários para administrador.");

                user.setRole(updateInput.getRole());

            }

            return userMapper.toUserProfileOutput(user);
        }

        // Verifica a senha informada
        if (!BcryptUtil.matches(updateInput.getCurrentPassword(), user.getPassword()))
            throw new ApiException(401, "Sua senha está incorreta.");

        if (updateInput.getName() != null)
            user.setName(updateInput.getName());

        if (updateInput.getNewPassword() != null)
            user.setPassword(BcryptUtil.bcryptHash(updateInput.getNewPassword()));

        return userMapper.toUserProfileOutput(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = getById(id);

        // Não é permitido deletar administradores.
        if (user.getRole() == Role.ADMIN)
            throw new ApiException(403, "Não é permitido remover outros administradores.");

        userRepository.delete(user);

        return;
    }

}
