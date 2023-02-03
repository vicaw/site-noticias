package dev.vicaw.service;

import java.util.List;
import java.util.UUID;

import dev.vicaw.model.Image;
import dev.vicaw.model.input.MultipartBody;
import dev.vicaw.model.output.ImageInfo;

public interface ImageService {
    public List<ImageInfo> listImageInfo();

    public List<ImageInfo> listImageInfoByArticleId(Long articleId);

    public ImageInfo getImageInfoById(UUID imageId);

    public ImageInfo getImageInfoByName(String imageName);

    public ImageInfo save(MultipartBody body);

    public ImageInfo update(MultipartBody body);

    public void deleteAllArticleImages(Long articleId);

    public void deleteImageByName(String name);

    public Image getImageById(UUID id);

    public Image getImageByName(String fileName);

    public Image getImageByNameAndScale(String name, int w, int h);

}
