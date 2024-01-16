package com.servletsRESTfulCRUDApp.controller;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibEventRepositoryImpl;
import com.servletsRESTfulCRUDApp.service.EventService;
import com.servletsRESTfulCRUDApp.service.impl.EventServiceImpl;
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
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Event", description = "Operations related to events")
public class EventServletRestController {

    private EventService service = new EventServiceImpl(new HibEventRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Create a new event",
            description = "Creates a new event with the data provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = INFO_EVENT_CREATED, content = @Content(schema = @Schema(implementation = Event.class))),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "Event object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = Event.class)))
    public Response createEvent(Event newEvent) {
        try {
            service.save(newEvent);
            return Response.status(Response.Status.CREATED).entity(newEvent).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get all events",
            description = "Returns a list of all events",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Events",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    public Response getAllEvents() {
        List<Event> events = service.findAll();
        return Response.ok(events).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Find event by ID",
            description = "For valid response try integer IDs. Other values will generated exceptions",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = INFO_EVENT_FOUND,
                            content = @Content(schema = @Schema(implementation = Event.class))),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_ID_SUPPLIED),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "404", description = ERR_EVENT_NOT_FOUND)
            }
    )
    public Response getById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        Optional<Event> optionalEvent = service.findById(id);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            return Response.status(Response.Status.OK).entity(event).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(ERR_EVENT_NOT_FOUND).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Update an event",
            description = "Updates an event",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_EVENT_UPDATED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_EVENT_DATA_IS_REQUIRED),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "Event object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = Event.class)))
    public Response updateEvent(Event event) {
        if (event == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_EVENT_DATA_IS_REQUIRED).build();
        }

        try {
            service.update(event);
            return Response.ok(INFO_EVENT_UPDATED_SUCCESSFULLY).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERR_UPDATING_EVENT + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete an event by ID",
            description = "Deletes an event with the specified ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_EVENT_DELETED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    public Response deleteEvent(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        try {
            service.deleteById(id);
            return Response.ok(INFO_EVENT_DELETED_SUCCESSFULLY).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERR_DELETING_EVENT + e.getMessage()).build();
        }
    }
}
