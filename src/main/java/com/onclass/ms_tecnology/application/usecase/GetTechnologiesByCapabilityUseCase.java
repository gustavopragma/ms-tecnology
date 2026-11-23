package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import reactor.core.publisher.Flux;

public class GetTechnologiesByCapabilityUseCase {

    private final TechnologyRepository technologyRepository;

    public GetTechnologiesByCapabilityUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Flux<Technology> getTechnologies(Long capabilityId) {
        return technologyRepository.findAllByCapabilityId(capabilityId);
    }
}
