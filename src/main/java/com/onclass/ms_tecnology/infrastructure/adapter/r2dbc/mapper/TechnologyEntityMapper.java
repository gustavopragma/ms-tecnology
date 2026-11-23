package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.mapper;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.TechnologyEntity;
import org.springframework.stereotype.Component;

@Component
public class TechnologyEntityMapper {
    public TechnologyEntity toEntity(Technology technology) {
        return new TechnologyEntity(technology.getId(), technology.getName(), technology.getDescription());
    }

    public Technology toDomain(TechnologyEntity technologyEntity) {
        return new Technology(technologyEntity.getId(), technologyEntity.getName(), technologyEntity.getDescription());
    }
}
