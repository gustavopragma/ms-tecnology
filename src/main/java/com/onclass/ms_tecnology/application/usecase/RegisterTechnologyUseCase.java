package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import reactor.core.publisher.Mono;

public class RegisterTechnologyUseCase {

    private final TechnologyRepository technologyRepository;

    public RegisterTechnologyUseCase(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Mono<Technology> registerTechnology(Technology technology) {
        return technologyRepository.existsByName(technology.getName())
            .flatMap(exists -> {
                if(exists) {
                    return Mono.error(new BusinessException(ErrorCode.TECHNOLOGY_ALREADY_EXISTS));
                }
                return technologyRepository.save(technology);
            });
    }
}
