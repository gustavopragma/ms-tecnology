package com.onclass.ms_tecnology.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyRequestDto {

    @Schema(description = "Unique name of the technology", example = "Java", maxLength = 50)
    @NotBlank(message = "name is required")
    @Size(max = 50, message = "name must not exceed 50 characters")
    private String name;

    @Schema(description = "Short description of the technology", example = "programming language", maxLength = 90)
    @NotBlank(message = "description is required")
    @Size(max = 90, message = "description must not exceed 90 characters")
    private String description;
}
