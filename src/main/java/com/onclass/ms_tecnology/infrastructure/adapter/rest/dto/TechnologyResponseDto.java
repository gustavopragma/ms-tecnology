package com.onclass.ms_tecnology.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyResponseDto {

    @Schema(description = "Unique identifier of the technology", example = "1")
    private Long id;

    @Schema(description = "Name of the technology", example = "Java")
    private String name;

    @Schema(description = "Description of the technology", example = "Programming language")
    private String description;
}
