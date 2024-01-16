package com.servletsRESTfulCRUDApp.controller;

import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibFileRepositoryImpl;
import com.servletsRESTfulCRUDApp.service.FileService;
import com.servletsRESTfulCRUDApp.service.impl.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.ERR_500_INTERNAL_SERVER_ERROR;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Entity.*;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.Entity.*;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "File", description = "Operations related to files")
public class FileServletRestController {

    private FileService service = new FileServiceImpl(new HibFileRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Create a new file",
            description = "Creates a new file with the data provided",
            responses = {
                    @ApiResponse(responseCode = "201", description = INFO_FILE_CREATED, content = @Content(schema = @Schema(implementation = File.class))),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "File object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = File.class)))
    public Response createFile(File newFile) {
        try {
            service.save(newFile);
            return Response.status(Response.Status.CREATED).entity(newFile).build();
        } catch (Exception e) {
            return Response.status(INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get all files",
            description = "Returns a list of all files",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Files",
                            content = @Content(schema = @Schema(implementation = List.class)))
            }
    )
    public Response getAllFiles(@Context UriInfo uriInfo) {
        List<FileResource> fileResources = new ArrayList<>();
        List<File> files = service.findAll();
        files.forEach(file -> {
            fileResources.add(getFileResource(file, uriInfo));
        });
        return Response.ok(fileResources).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Find file by ID",
            description = "For valid response try integer IDs. Other values will generated exceptions",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = INFO_FILE_FOUND,
                            content = @Content(schema = @Schema(implementation = File.class))),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "404", description = ERR_FILE_NOT_FOUND)
            }
    )
    public Response getById(@PathParam("id") Long id, @Context UriInfo uriInfo) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        Optional<File> optionalFile = service.findById(id);

        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            FileResource fileResource = getFileResource(file, uriInfo);
            return Response.status(Response.Status.OK).entity(fileResource).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(ERR_FILE_NOT_FOUND).build();
        }
    }

    private static FileResource getFileResource(File file, UriInfo uriInfo) {
        FileResource fileResource = new FileResource();
        fileResource.setFile(file);

        List<Link> links = new ArrayList<>();

        URI selfUri = uriInfo
                .getBaseUriBuilder()
                .path(FileServletRestController.class)
                .path(String.valueOf(file.getId()))
                .build();

        Link selfLink = Link.fromUri(selfUri).rel("self").build();
        links.add(selfLink);

        URI downloadUri = uriInfo
                .getBaseUriBuilder()
                .path(FileStorageServletRestController.class)
                .path("/download")
                .path(file.getName())
                .build();
        Link downloadLink = Link.fromUri(downloadUri).rel("download").build();
        links.add(downloadLink);

        fileResource.setLinks(links);
        return fileResource;
    }

    @Getter
    @Setter
    private static class FileResource {
        private File file;
        private List<Link> links;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Update a file",
            description = "Updates a file",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_FILE_UPDATED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_FILE_DATA_IS_REQUIRED),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    @RequestBody(
            description = "File object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = File.class)))
    public Response updateFile(File file) {
        if (file == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_FILE_DATA_IS_REQUIRED).build();
        }

        try {
            Optional<File> fileOptional = service.update(file);
            if (fileOptional.isPresent()) {
                return Response.ok(INFO_FILE_UPDATED_SUCCESSFULLY).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(ERR_FILE_NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(INTERNAL_SERVER_ERROR).entity(ERR_UPDATING_FILE + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete a file by ID",
            description = "Deletes a file with the specified ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = INFO_FILE_DELETED_SUCCESSFULLY),
                    @ApiResponse(responseCode = "400", description = ERR_INVALID_PATH_ID_IS_REQUIRED),
                    @ApiResponse(responseCode = "404", description = ERR_FILE_NOT_FOUND),
                    @ApiResponse(responseCode = "500", description = ERR_500_INTERNAL_SERVER_ERROR)
            }
    )
    public Response deleteFile(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ERR_INVALID_PATH_ID_IS_REQUIRED).build();
        }

        if (service.findById(id).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ERR_FILE_NOT_FOUND).build();
        }

        try {
            service.deleteById(id);
        } catch (Exception e) {
            return Response.status(INTERNAL_SERVER_ERROR).entity(ERR_DELETING_FILE + e.getMessage()).build();
        }
        return Response.ok(INFO_FILE_DELETED_SUCCESSFULLY).build();
    }
}
