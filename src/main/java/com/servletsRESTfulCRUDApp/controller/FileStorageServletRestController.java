package com.servletsRESTfulCRUDApp.controller;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.impl.FileStorageRepositoryImpl;
import com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibEventRepositoryImpl;
import com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibUserRepositoryImpl;
import com.servletsRESTfulCRUDApp.service.EventService;
import com.servletsRESTfulCRUDApp.service.FileStorageService;
import com.servletsRESTfulCRUDApp.service.UserService;
import com.servletsRESTfulCRUDApp.service.impl.EventServiceImpl;
import com.servletsRESTfulCRUDApp.service.impl.FileStorageServiceImpl;
import com.servletsRESTfulCRUDApp.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.repository.impl.FileStorageRepositoryImpl.PROPERTY_FILE_STORAGE_DIR;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.ERR_500_INTERNAL_SERVER_ERROR;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Entity.ERR_USER_NOT_FOUND;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.Entity.INFO_FILE_UPLOADED;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.FileStorage.*;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Path("/file-storage")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "FileStorage", description = "Operations related to file-storage")
public class FileStorageServletRestController {

    private FileStorageService fileStorageService = new FileStorageServiceImpl(new FileStorageRepositoryImpl());
    private EventService eventService = new EventServiceImpl(new HibEventRepositoryImpl());
    private UserService userService = new UserServiceImpl(new HibUserRepositoryImpl());

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Upload a file to file storage",
            description = "Uploads a file with the data provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = INFO_FILE_UPLOADED, content = @Content(schema = @Schema(implementation = Event.class))),
                    @ApiResponse(responseCode = "404", description = ERR_USER_NOT_FOUND),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    public Response uploadFile(
            @FormDataParam("file") FormDataContentDisposition fileDetail,

            @Parameter(description = "File for uploading", schema = @Schema(type="string", format = "binary"))
            @FormDataParam("file") InputStream inputStream,

            @Parameter(description = "User ID", schema = @Schema(type = "integer"))
            @FormDataParam("userId") Long userId) {

        try {
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(ERR_USER_NOT_FOUND).build();
            }

            String fileName = fileDetail.getFileName();
            fileStorageService.uploadUserFileToStorage(inputStream, fileName);

            log.info(INFO_STARTING_TO_SAVE_FILE_REPOSITORY_S_EVENT_ENTITY);
            User existingUser = optionalUser.get();
            Event event = eventService.prepareNewEntity(existingUser, fileName);
            Event savedEvent = eventService.save(event).orElse(null);
            log.info(INFO_FILE_REPOSITORY_S_EVENT_ENTITY_SAVED_SUCCESSFULLY);

            return Response.status(Response.Status.CREATED).entity(savedEvent).build();
        } catch (Exception e) {
            return Response.status(INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/download/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Operation(
            summary = "Download a file from file storage by fileName",
            description = "Downloads a file by fileName provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = INFO_FILE_DOWNLOADED_SUCCESSFULLY, content = @Content(schema = @Schema(implementation = Event.class))),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    public Response downloadFile(@PathParam("fileName") String fileName) {
        return fileStorageService.downloadUserFileFromStorage(fileName);
    }
}
