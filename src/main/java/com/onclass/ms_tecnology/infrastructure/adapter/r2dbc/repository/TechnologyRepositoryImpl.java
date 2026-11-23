package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.repository;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.mapper.TechnologyEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TechnologyRepositoryImpl implements TechnologyRepository {
    private final MySqlTechnologyRepository repository;
    private final TechnologyEntityMapper mapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return repository.save(mapper.toEntity(technology))
            .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByNameIgnoreCase(name);
    }
}
