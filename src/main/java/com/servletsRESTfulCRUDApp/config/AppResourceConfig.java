package com.servletsRESTfulCRUDApp.config;

import com.servletsRESTfulCRUDApp.controller.EventServletRestController;
import com.servletsRESTfulCRUDApp.controller.FileServletRestController;
import com.servletsRESTfulCRUDApp.controller.FileStorageServletRestController;
import com.servletsRESTfulCRUDApp.controller.UserServletRestController;
import com.servletsRESTfulCRUDApp.json.GsonMessageBodyHandler;
import com.servletsRESTfulCRUDApp.json.GsonMessageBodyReader;
import com.servletsRESTfulCRUDApp.json.GsonUserSerializerAdapter;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import static com.servletsRESTfulCRUDApp.config.OpenApiSwaggerConfig.initOpenApiSwaggerWithJersey;

@ApplicationPath("/rest/api/v1")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {

        // JAX-RS components
        register(UserServletRestController.class);
        register(FileServletRestController.class);
        register(EventServletRestController.class);
        register(FileStorageServletRestController.class);

        // Json handlers
        register(GsonMessageBodyHandler.class);
        register(GsonMessageBodyReader.class);
        register(GsonUserSerializerAdapter.class);

        // OpenApi and Swagger
        initOpenApiSwaggerWithJersey();
        register(OpenApiResource.class);
        register(MultiPartFeature.class);

        // CORS filter
        register(CORSFilter.class);
    }
}
