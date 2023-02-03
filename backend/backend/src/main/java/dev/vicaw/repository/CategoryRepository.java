package dev.vicaw.repository;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import dev.vicaw.model.category.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public Optional<Category> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

}
