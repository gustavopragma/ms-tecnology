package com.onclass.ms_tecnology.infrastructure.adapter.rest.mapper;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TechnologyDtoMapper {
    public Technology toDomain(TechnologyRequestDto technologyRequestDto) {
        return new Technology(null, technologyRequestDto.getName(), technologyRequestDto.getDescription());
    }

    public TechnologyResponseDto toResponse(Technology technology) {
        return new TechnologyResponseDto(technology.getId(), technology.getName(), technology.getDescription());
    }
}
