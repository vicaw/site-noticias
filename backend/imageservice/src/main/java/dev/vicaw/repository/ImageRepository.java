package dev.vicaw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.Image;
import dev.vicaw.model.output.ImageInfo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ImageRepository implements PanacheRepository<Image> {
    public Optional<Image> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public List<ImageInfo> listImageInfo() {
        return findAll().project(ImageInfo.class).list();
    }

    public List<ImageInfo> listImageInfoByArticleId(Long articleId) {
        return find("article_id", articleId).project(ImageInfo.class).list();
    }

    public Optional<ImageInfo> findImageInfoById(UUID id) {
        return find("id", id).project(ImageInfo.class).firstResultOptional();
    }

    public Optional<ImageInfo> findImageInfoByName(String name) {
        return find("name", name).project(ImageInfo.class).firstResultOptional();
    }

}
