package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class DeleteTechnologiesByCapabilitiesUseCase {
    private final TechnologyRepository technologyRepository;

    public DeleteTechnologiesByCapabilitiesUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Mono<Void> deleteByCapabilityIds(List<Long> capabilityIds) {
        if (capabilityIds == null || capabilityIds.isEmpty()) {
            return Mono.empty();
        }

        return technologyRepository.findTechnologyIdsByCapabilityIds(capabilityIds)
            .distinct()
            .collectList()
            .flatMap(techIdsCandidateToDelete -> {
                if (techIdsCandidateToDelete.isEmpty()) {
                    return Mono.empty();
                }
                return technologyRepository.deleteAllByCapabilityIds(capabilityIds)
                    .thenMany(Flux.fromIterable(techIdsCandidateToDelete))
                    .filterWhen(technologyId -> technologyRepository.countByTechnologyId(technologyId).map(count -> count == 0))
                    .collectList()
                    .flatMap(deleteTechIds -> {
                        if (deleteTechIds.isEmpty()) {
                            return Mono.empty();
                        }
                        return technologyRepository.deleteAllById(deleteTechIds);
                    });
            });
    }
}
