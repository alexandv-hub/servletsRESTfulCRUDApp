package com.servletsRESTfulCRUDApp.config;

import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

import static com.servletsRESTfulCRUDApp.config.DBConnection.getProperties;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.ERR_SWAGGER_INIT_FAILED;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_STARTING_INIT_SWAGGER;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_SWAGGER_INIT_FINISHED_SUCCESSFULLY;

@Slf4j
public class OpenApiSwaggerConfig {

    private static final String PROPERTY_OPENAPI_REQUEST_URL = getProperties().getProperty("openapi.request.url");

    private static OpenAPI createOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Example app CRUD RESTful API")
                        .description("API for accessing entity data and file storage")
                        .version("v1"))
                .addServersItem(new Server().url(PROPERTY_OPENAPI_REQUEST_URL));
    }

    private static SwaggerConfiguration createSwaggerConfig() {
        OpenAPI oas = createOpenApi();

        return new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true);
    }

    static void initOpenApiSwaggerWithJersey() {
        log.info(INFO_STARTING_INIT_SWAGGER);
        SwaggerConfiguration oasConfig = OpenApiSwaggerConfig.createSwaggerConfig();
        try {
            OpenApiContext openApiContext = new GenericOpenApiContext<>()
                    .openApiConfiguration(oasConfig)
                    .init();
            OpenApiContextLocator.getInstance().putOpenApiContext(OpenApiSwaggerConfig.class.getSimpleName(), openApiContext);
        } catch (Exception e) {
            log.error(ERR_SWAGGER_INIT_FAILED);
            e.printStackTrace(System.out);
        }
        log.info(INFO_SWAGGER_INIT_FINISHED_SUCCESSFULLY);
    }
}
