package com.onclass.ms_tecnology.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Body to associate technologies with a capability")
public class AssociateCapabilityRequestDto {

    @Schema(description = "id of the capability", example = "1")
    @NotNull(message = "capability ID is required")
    private Long capabilityId;

    @Schema(description = "List of technology ids to associate", example = "[1, 2, 3]")
    @NotEmpty(message = "technologyIds list cannot be empty")
    private List<Long> technologyIds;
}
