package com.onclass.ms_tecnology.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTechnologiesByCapabilitiesRequestDto {
    @NotEmpty(message = "capabilityIds cannot be empty")
    private List<Long> capabilityIds;
}
