package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssociateCapabilityTechnologiesUseCase {
    private final TechnologyRepository technologyRepository;

    public AssociateCapabilityTechnologiesUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Mono<Void> associate(Long capabilityId, List<Long> technologyIds) {
        Set<Long> uniqueIds = new HashSet<>(technologyIds);

        return technologyRepository.findExistingIds(uniqueIds)
            .collect(Collectors.toSet())
            .flatMap(existingIds -> {
                if (existingIds.size() != uniqueIds.size()) {
                    Set<Long> missingIds = new HashSet<>(uniqueIds);
                    missingIds.removeAll(existingIds);
                    return Mono.error(new BusinessException(
                        ErrorCode.TECHNOLOGY_NOT_FOUND,
                        "Technologies not found with ids: " + missingIds
                    ));
                }
                return technologyRepository
                    .associateTechnologiesToCapability(capabilityId, List.copyOf(uniqueIds));
            });
    }
}
