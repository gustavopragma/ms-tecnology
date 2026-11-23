package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.repository;

import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.CapabilityTechnologyEntity;
import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MySqlCapabilityTechnologyRepository extends ReactiveCrudRepository<CapabilityTechnologyEntity, Long> {

    @Query(
        "SELECT t.* FROM technologies t " +
        "INNER JOIN capability_technologies ct ON t.id = ct.id_technology " +
        "WHERE ct.id_capability = :capabilityId"
    )
    Flux<TechnologyEntity> findTechnologiesByCapabilityId(Long capabilityId);
}
