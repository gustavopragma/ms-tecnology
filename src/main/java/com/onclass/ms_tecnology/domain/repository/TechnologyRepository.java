package com.onclass.ms_tecnology.domain.repository;

import com.onclass.ms_tecnology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

public interface TechnologyRepository {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
    Flux<Long> findExistingIds(Collection<Long> technologyIds);
    Mono<Void> associateTechnologiesToCapability(Long capabilityId, List<Long> technologyIds);
    Flux<Technology> findAllByCapabilityId(Long capabilityId);
}
