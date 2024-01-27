package com.servletsRESTfulCRUDApp.controller;

import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibUserRepositoryImpl;
import com.servletsRESTfulCRUDApp.service.UserService;
import com.servletsRESTfulCRUDApp.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.ERR_500_INTERNAL_SERVER_ERROR;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Entity.*;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.Entity.*;

@AllArgsConstructor
@NoArgsConstructor
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "Operations related to users")
public class UserServletRestController {

    private UserService service = new UserServiceImpl(new HibUserRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the data provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = INFO_USER_CREATED, content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "User object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class)))
    public Response createUser(User newUser) {
        try {
            User savedUser = service.save(newUser).orElse(null);
            return Response.status(Response.Status.CREATED).entity(savedUser).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Find user by ID",
            description = "For valid response try integer IDs. Other values will generated exceptions",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = INFO_USER_FOUND,
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "404", description = ERR_USER_NOT_FOUND)
            }
    )
    public Response getById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        Optional<User> optionalUser = service.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(ERR_USER_NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Users",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    public Response getAllUsers() {
        List<User> users = service.findAll();
        return Response.ok(users).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Update a user",
            description = "Updates a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_USER_UPDATED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_USER_DATA_IS_REQUIRED),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "User object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class)))
    public Response updateUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_USER_DATA_IS_REQUIRED).build();
        }

        try {
            service.update(user);
            return Response.ok(INFO_USER_UPDATED_SUCCESSFULLY).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERR_UPDATING_USER + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user with the specified ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_USER_DELETED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    public Response deleteUser(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        if (service.findById(id).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ERR_USER_NOT_FOUND).build();
        }

        try {
            service.deleteById(id);
            return Response.ok(INFO_USER_DELETED_SUCCESSFULLY).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERR_DELETING_USER + e.getMessage()).build();
        }
    }
}
