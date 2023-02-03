package dev.vicaw.model.article.input;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import lombok.Data;

@Data
public class ArticleFormInput {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream coverImage;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String coverImageName;

    // É convertido para JSON no resource.
    @FormParam("article")
    @PartType(MediaType.TEXT_PLAIN)
    public String article;
}
