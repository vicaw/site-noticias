package dev.vicaw.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.vicaw.exception.ApiException;
import dev.vicaw.model.article.input.ArticleFormInput;
import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.input.ArticleUpdateInput;
import dev.vicaw.service.ArticleService;

@Path("/api/articles")
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @Inject
    JsonWebToken token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("authorId") Long authorId) {
        System.out.println("AUtorid " + authorId);
        return Response.status(Status.OK).entity(articleService.list(authorId)).build();
    }

    @POST
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@MultipartForm ArticleFormInput body) {
        // Mapeia JSON para um objeto.
        ObjectMapper mapper = new ObjectMapper();
        ArticleInput articleInput = null;

        System.out.println(body.getArticle());

        try {
            articleInput = mapper.readValue(body.getArticle(), ArticleInput.class);
        } catch (JacksonException e) {
            throw new ApiException(400, "Falha ao mapear objeto.");
        }

        if (!token.getSubject().equals(articleInput.getAuthorId().toString()))
            throw new ApiException(400, "O ID do autor não é o mesmo do usuário solicitante.");

        return Response.status(Status.OK)
                .entity(articleService.create(body.getCoverImage(), body.getCoverImageName(), articleInput)).build();
    }

    @Path("/{articleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("articleId") Long articleId) {
        return Response.status(Status.OK).entity(articleService.getById(articleId)).build();
    }

    @Path("/{articleId}")
    @PUT
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("articleId") Long articleId, @MultipartForm ArticleFormInput body) {
        // Mapeia JSON para um objeto.
        ObjectMapper mapper = new ObjectMapper();
        ArticleUpdateInput articleInput = null;

        System.out.println(body.getArticle());

        try {
            articleInput = mapper.readValue(body.getArticle(), ArticleUpdateInput.class);
        } catch (JacksonException e) {
            throw new ApiException(400, "Falha ao mapear objeto.");
        }

        return Response.status(Status.OK)
                .entity(articleService.update(articleId, body.getCoverImage(), body.getCoverImageName(), articleInput))
                .build();
    }

    @Path("/{articleId}")
    @DELETE
    @RolesAllowed({ "EDITOR", "ADMIN" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("articleId") Long articleId) {
        articleService.delete(articleId);
        return Response.status(Status.OK).build();
    }

    @Path("/slugs/{articleSlug}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySlug(@PathParam("articleSlug") String articleSlug) {
        return Response.status(Status.OK).entity(articleService.getBySlug(articleSlug)).build();
    }

    @Path("/feedinfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedInfo(@DefaultValue("10") @QueryParam("pagesize") int pagesize,
            @DefaultValue("0") @QueryParam("page") int page, @QueryParam("category") String categorySlug) {
        return Response.status(Status.OK).entity(articleService.getFeedInfo(pagesize, page, categorySlug)).build();
    }

    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchArticle(
            @QueryParam("q") @NotNull(message = "O termo de busca não foi informado.") @Size(min = 3, message = "O termo de busca deve ser maior que 3 caracteres") String query,
            @DefaultValue("10") @QueryParam("pagesize") int pagesize,
            @DefaultValue("0") @QueryParam("page") int page) {
        System.out.println(query);
        return Response.status(Status.OK).entity(articleService.searchArticle(query, pagesize, page)).build();
    }
}
