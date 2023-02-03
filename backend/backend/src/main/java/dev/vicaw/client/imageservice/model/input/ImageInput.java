package dev.vicaw.client.imageservice.model.input;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageInput {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    @FormParam("article_id")
    @PartType(MediaType.TEXT_PLAIN)
    public Long article_id;

}
