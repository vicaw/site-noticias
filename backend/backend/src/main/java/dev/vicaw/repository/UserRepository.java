package dev.vicaw.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.user.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

}
