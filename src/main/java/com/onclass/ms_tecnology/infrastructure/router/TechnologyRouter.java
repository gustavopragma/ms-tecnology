package com.onclass.ms_tecnology.infrastructure.router;

import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyResponseDto;
import com.onclass.ms_tecnology.infrastructure.exceptionHandler.ErrorResponse;
import com.onclass.ms_tecnology.infrastructure.handler.TechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TechnologyRouter {

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/technologies",
            method = RequestMethod.POST,
            beanClass = TechnologyHandler.class,
            beanMethod = "register",
            operation = @Operation(
                operationId = "registerTechnology",
                summary = "Register a new technology (US-1)",
                tags = {"Technologies"},
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = TechnologyRequestDto.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "201", description = "Technology created successfully",
                        content = @Content(schema = @Schema(implementation = TechnologyResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data or duplicate technology",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
                }
            )
        )
    })
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandler handler) {
        return route(POST("/api/v1/technologies"), handler::register);
    }
}
