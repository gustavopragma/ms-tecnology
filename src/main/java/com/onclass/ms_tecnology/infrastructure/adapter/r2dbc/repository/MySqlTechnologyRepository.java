package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.repository;

import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MySqlTechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
}
