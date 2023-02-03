package dev.vicaw.client.imageservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import dev.vicaw.client.imageservice.model.input.ImageInput;
import io.quarkus.oidc.token.propagation.AccessToken;

@RegisterRestClient(baseUri = "http://localhost:8081/api")
@AccessToken
public interface ImageServiceClient {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@MultipartForm ImageInput body);

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@MultipartForm ImageInput body);

    @DELETE
    @Path("/articles/{articleId}")
    public Response deleteAllByArticleId(@PathParam("articleId") Long articleId);

}
