package dev.vicaw.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
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

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.vicaw.exception.ApiException;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.input.UserUpdateInput;
import dev.vicaw.service.UserService;

@Path("/api/users")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken token;

    @GET
    @RolesAllowed({ "ADMIN" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(userService.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid UserCreateInput userInput) {
        return Response.status(Status.OK).entity(userService.create(userInput)).build();
    }

    @Path("/{userId}")
    @GET
    @RolesAllowed({ "USER", "EDITOR", "ADMIN" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("userId") Long userId) {

        // USER e EDITOR só podem consultar o próprio ID.
        boolean isAdmin = token.getGroups().contains("ADMIN");
        if (!isAdmin) {
            if (!token.getSubject().equals(userId.toString())) {
                throw new ApiException(403, "Você não pode acessar esse conteúdo");
            }
        }

        return Response.status(Status.OK).entity(userService.getById(userId)).build();
    }

    @Path("/{userId}")
    @PUT
    @RolesAllowed({ "USER", "EDITOR", "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("userId") Long userId, @Valid UserUpdateInput updateInput) {

        // Apenas ADMIN pode editar outros usuários além dele.
        boolean isAdmin = token.getGroups().contains("ADMIN");
        if (!isAdmin) {
            if (!token.getSubject().equals(userId.toString())) {
                throw new ApiException(403, "Você não pode editar outros usuários.");
            }
        }

        return Response.status(Status.OK).entity(userService.update(userId, updateInput)).build();
    }

    @Path("/{userId}")
    @DELETE
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("userId") Long userId) {
        userService.delete(userId);
        return Response.status(Status.OK).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserLoginInput loginInput) {
        return Response.status(Status.OK).entity(userService.login(loginInput)).build();
    }

    @Path("/profiles/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileById(@PathParam("userId") Long userId) {
        return Response.status(Status.OK).entity(userService.getUserProfileById(userId)).build();
    }

}
