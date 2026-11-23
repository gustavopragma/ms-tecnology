package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.repository;

import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface MySqlTechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {
    Mono<Boolean> existsByNameIgnoreCase(String name);

    @Query("SELECT id FROM technologies WHERE id IN (:ids)")
    Flux<Long> findExistingIds(Collection<Long> ids);
}
