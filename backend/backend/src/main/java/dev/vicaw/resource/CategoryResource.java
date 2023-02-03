package dev.vicaw.resource;

import javax.annotation.security.PermitAll;
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

import dev.vicaw.model.category.input.CategoryInput;
import dev.vicaw.service.CategoryService;

@Path("/api/categories")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Status.OK).entity(categoryService.list()).build();
    }

    @POST
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid CategoryInput input) {
        return Response.status(Status.OK).entity(categoryService.create(input)).build();
    }

    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("categoryId") Long categoryId) {
        return Response.status(Status.OK).entity(categoryService.getById(categoryId)).build();
    }

    @PUT
    @Path("/{categoryId}")
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("categoryId") Long categoryId, @Valid CategoryInput input) {
        return Response.status(Status.OK).entity(categoryService.update(categoryId, input)).build();
    }

    @DELETE
    @Path("/{categoryId}")
    @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return Response.status(Status.OK).build();
    }

    @GET
    @Path("/slugs/{categorySlug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySlug(@PathParam("categorySlug") String categorySlug) {
        return Response.status(Status.OK).entity(categoryService.getBySlug(categorySlug)).build();
    }

}
