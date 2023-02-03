package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
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

import dev.vicaw.exception.ApiException;
import dev.vicaw.model.comment.input.CommentEditInput;
import dev.vicaw.model.comment.input.CommentInput;

import dev.vicaw.service.CommentService;

@Path("/api/comments")
@RolesAllowed({ "USER", "EDITOR", "ADMIN" })
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    JsonWebToken token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(commentService.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid CommentInput commentInput) {

        if (!token.getSubject().equals(commentInput.getAuthorId().toString()))
            throw new ApiException(400, "O ID do autor não é o mesmo do usuário solicitante.");

        return Response.status(Status.OK).entity(commentService.create(commentInput)).build();
    }

    @Path("/{commentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("commentId") Long commentId) {
        return Response.status(Status.OK).entity(commentService.getById(commentId)).build();
    }

    @Path("/{commentId}")
    @PUT
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("commentId") Long commentId, @Valid CommentEditInput input) {
        return Response.status(Status.OK).entity(commentService.update(commentId, input)).build();
    }

    @Path("/{commentId}")
    @DELETE
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("commentId") Long commentId) {
        commentService.delete(commentId);
        return Response.status(Status.OK).build();
    }

    @Path("/articles/{articleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleComments(
            @PathParam("articleId") Long articleId,
            @DefaultValue("10") @QueryParam("pagesize") int pagesize,
            @DefaultValue("0") @QueryParam("page") int page) {
        return Response.status(Status.OK).entity(commentService.getArticleComments(articleId, pagesize, page)).build();
    }

    @Path("/articles/{articleId}/count")
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleCommentsCount(@PathParam("articleId") Long articleId) {
        return Response.status(Status.OK).entity(commentService.getArticleCommentsCount(articleId)).build();
    }
}
