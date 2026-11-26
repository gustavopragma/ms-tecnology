package com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.repository;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.entity.CapabilityTechnologyEntity;
import com.onclass.ms_tecnology.infrastructure.adapter.r2dbc.mapper.TechnologyEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TechnologyRepositoryImpl implements TechnologyRepository {
    private final MySqlTechnologyRepository technologyRepository;
    private final MySqlCapabilityTechnologyRepository mySqlCapabilityTechnologyRepository;
    private final TechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology))
            .map(technologyEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Flux<Long> findExistingIds(Collection<Long> technologyIds) {
        return technologyRepository.findExistingIds(technologyIds);
    }

    @Override
    public Mono<Void> associateTechnologiesToCapability(Long capabilityId, List<Long> technologyIds) {
        List<CapabilityTechnologyEntity> entities = technologyIds.stream()
            .map(techId -> new CapabilityTechnologyEntity(null, capabilityId,  techId))
            .toList();

        return mySqlCapabilityTechnologyRepository.saveAll(entities).then();
    }

    @Override
    public Flux<Technology> findAllByCapabilityId(Long capabilityId) {
        return mySqlCapabilityTechnologyRepository
            .findTechnologiesByCapabilityId(capabilityId)
            .map(technologyEntityMapper::toDomain);
    }

    @Override
    public Flux<Long> findTechnologyIdsByCapabilityIds(List<Long> capabilityIds) {
        return mySqlCapabilityTechnologyRepository.findTechnologyIdsByCapabilityIds(capabilityIds);
    }

    @Override
    public Mono<Void> deleteAllByCapabilityIds(List<Long> capabilityIds) {
        return mySqlCapabilityTechnologyRepository.deleteAllByCapabilityIds(capabilityIds);
    }

    @Override
    public Mono<Long> countByTechnologyId(Long technologyId) {
        return mySqlCapabilityTechnologyRepository.countByTechnologyId(technologyId);
    }

    @Override
    public Mono<Void> deleteAllById(List<Long> ids) {
        return technologyRepository.deleteAllById(ids);
    }
}
