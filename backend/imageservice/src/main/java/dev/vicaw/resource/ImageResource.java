package dev.vicaw.resource;

import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import dev.vicaw.model.Image;
import dev.vicaw.model.input.MultipartBody;
import dev.vicaw.service.ImageService;

@Path("/")
public class ImageResource {

    @Inject
    ImageService imageService;

    @POST
    @Path("/api")
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@MultipartForm MultipartBody body) {
        return Response.status(Status.OK).entity(imageService.save(body)).build();
    }

    @PUT
    @Path("/api")
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@MultipartForm MultipartBody body) {
        return Response.status(Status.OK).entity(imageService.update(body)).build();
    }

    // Info - JSON com informações das imagens.
    @GET
    @Path("/api/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listInfo() {
        return Response.status(Status.OK).entity(imageService.listImageInfo()).build();
    }

    @GET
    @Path("/api/{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfoById(@PathParam("imageId") UUID imageId) {
        return Response.status(Status.OK).entity(imageService.getImageInfoById(imageId)).build();
    }

    @GET
    @Path("/api/articles/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listInfoByArticleId(@PathParam("articleId") Long articleId) {
        return Response.status(Status.OK).entity(imageService.listImageInfoByArticleId(articleId)).build();
    }

    @DELETE
    @Path("/api/articles/{articleId}")
    @RolesAllowed({ "EDITOR", "ADMIN" })
    public Response deleteAllByArticleId(@PathParam("articleId") Long articleId) {
        imageService.deleteAllArticleImages(articleId);
        return Response.status(Status.OK).build();
    }

    @GET
    @Path("/api/names/{imageName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfoByName(@PathParam("imageName") String imageName) {
        return Response.status(Status.OK).entity(imageService.getImageInfoByName(imageName)).build();
    }

    @DELETE
    @Path("/api/names/{imageName}")
    @RolesAllowed({ "EDITOR", "ADMIN" })
    public Response deleteByName(@PathParam("imageName") String imageName) {
        imageService.deleteImageByName(imageName);
        return Response.status(Status.OK).build();
    }

    // Retorna a Imagem pura.
    @Path("images/{fileName}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImagebyName(@PathParam("fileName") String name) {
        Image image = imageService.getImageByName(name);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

    @Path("images/{width}/{height}/{fileName}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/jpg")
    public Response getImageByNameAndScale(@PathParam("fileName") String name, @PathParam("width") int w,
            @PathParam("height") int h) {
        Image image = imageService.getImageByNameAndScale(name, w, h);
        return Response.status(Status.OK).entity(image.getData()).build();
    }

}
