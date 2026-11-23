package com.onclass.ms_tecnology.domain.repository;

import com.onclass.ms_tecnology.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyRepository {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
}
