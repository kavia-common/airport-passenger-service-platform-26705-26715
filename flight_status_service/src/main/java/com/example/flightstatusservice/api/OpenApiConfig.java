package com.example.flightstatusservice.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI metadata configuration for swagger-ui.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI flightStatusServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flight Status Service API")
                        .description("Provides CRUD and query endpoints for flights, gates, and baggage belts.")
                        .version("0.1.0"));
    }
}
