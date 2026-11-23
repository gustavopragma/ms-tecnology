package com.onclass.ms_tecnology.infrastructure.exceptionHandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "400")
    private final Integer statusCode;

    @Schema(description = "Application error code", example = "SE_001")
    private final String code;

    @Schema(description = "Detailed error message", example = "Technology name already exists")
    private final String description;
}
