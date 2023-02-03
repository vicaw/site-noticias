package dev.vicaw.service.impl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.exception.ApiException;
import dev.vicaw.model.Image;
import dev.vicaw.model.input.MultipartBody;
import dev.vicaw.model.output.ImageInfo;
import dev.vicaw.repository.ImageRepository;
import dev.vicaw.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ImageServiceImpl implements ImageService {

    @Inject
    ImageRepository imageRepository;

    @Transactional
    @Override
    public ImageInfo save(MultipartBody body) {
        try {

            Image image = new Image();
            image.setData(body.file.readAllBytes());
            image.setName(UUID.randomUUID().toString() + "-" + body.fileName);
            image.setArticle_id(Long.valueOf(body.article_id));
            imageRepository.persist(image);
            return new ImageInfo(image.getId(), image.getName(), image.getArticle_id(), image.getCreatedAt(),
                    image.getUpdatedAt());

        } catch (IOException e) {
            throw new ApiException(500, "I/O Exception Error");
        } catch (OutOfMemoryError e) {
            throw new ApiException(500, "Out of Memory Error");
        }

    }

    @Transactional
    @Override
    public ImageInfo update(MultipartBody body) {
        try {

            Optional<Image> imageResult = imageRepository.findByName(body.fileName);

            if (imageResult.isEmpty())
                throw new ApiException(404, "Não existe nenhuma imagem com o nome informado.");

            Image image = imageResult.get();

            image.setData(body.file.readAllBytes());
            image.setName(UUID.randomUUID().toString() + "-" + body.fileName);

            imageRepository.persist(image);

            return new ImageInfo(image.getId(), image.getName(), image.getArticle_id(), image.getCreatedAt(),
                    image.getUpdatedAt());

        } catch (IOException e) {

            throw new ApiException(500, "I/O Exception Error");

        } catch (OutOfMemoryError e) {

            throw new ApiException(500, "Out of Memory Error");
        }
    }

    @Transactional
    @Override
    public void deleteImageByName(String name) {
        Optional<Image> imageResult = imageRepository.findByName(name);
        if (imageResult.isEmpty())
            throw new ApiException(404, "Não existe nenhuma imagem com o Nome informado.");

        imageRepository.delete(imageResult.get());
    }

    @Transactional
    @Override
    public void deleteAllArticleImages(Long articleId) {
        Optional<Image> imageResult = imageRepository.find("article_id", articleId)
                .firstResultOptional();

        if (imageResult.isEmpty())
            throw new ApiException(404, "Não existe nenhuma imagem vinculada a um artigo com o ID informado");

        imageRepository.delete("article_id", articleId);
        return;
    }

    @Override
    public List<ImageInfo> listImageInfo() {
        return imageRepository.listImageInfo();
    }

    @Override
    public ImageInfo getImageInfoById(UUID imageId) {
        Optional<ImageInfo> image = imageRepository.findImageInfoById(imageId);
        if (image.isEmpty())
            throw new ApiException(404, "Não existe nenhuma imagem com o ID informado.");

        return image.get();
    }

    @Override
    public ImageInfo getImageInfoByName(String imageName) {
        Optional<ImageInfo> image = imageRepository.findImageInfoByName(imageName);
        if (image.isEmpty())
            throw new ApiException(404, "Não existe nenhuma imagem com o Nome informado.");

        return image.get();
    }

    @Override
    public List<ImageInfo> listImageInfoByArticleId(Long articleId) {
        Optional<Image> imageResult = imageRepository.find("article_id", articleId)
                .firstResultOptional();

        if (imageResult.isEmpty())
            throw new ApiException(404, "Não existe nenhuma imagem vinculada a um artigo com o ID informado");

        return imageRepository.listImageInfoByArticleId(articleId);
    }

    @Override
    public Image getImageById(UUID uuid) {
        return new Image();
    }

    @Override
    public Image getImageByName(String fileName) {
        Optional<Image> imageResult = imageRepository.findByName(fileName);

        if (imageResult.isEmpty())
            return Image.defaultImage();

        return imageResult.get();
    }

    @Override
    public Image getImageByNameAndScale(String name, int w, int h) {
        Image image = getImageByName(name);
        image.scale(w, h);
        return image;
    }

}
