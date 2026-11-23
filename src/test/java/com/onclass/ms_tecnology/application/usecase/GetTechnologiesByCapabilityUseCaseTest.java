package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTechnologiesByCapabilityUseCaseTest {

    @Mock
    private TechnologyRepository technologyRepository;

    @InjectMocks
    private GetTechnologiesByCapabilityUseCase getTechnologiesByCapabilityUseCase;

    @Test
    void getTechnologiesByCapabilityIdSuccess() {
        Long capabilityId = 1L;
        Technology java = new Technology(1L, "Java", "Backend programming language");
        Technology spring = new Technology(2L, "Spring WebFlux", "Reactive web framework");

        when(technologyRepository.findAllByCapabilityId(capabilityId))
            .thenReturn(Flux.just(java, spring));

        StepVerifier.create(getTechnologiesByCapabilityUseCase.getTechnologies(capabilityId))
            .expectNext(java, spring)
            .verifyComplete();

        verify(technologyRepository).findAllByCapabilityId(capabilityId);
    }

    @Test
    void getTechnologiesByCapabilityIdWhenEmpty() {
        Long capabilityId = 99L;

        when(technologyRepository.findAllByCapabilityId(capabilityId))
            .thenReturn(Flux.empty());

        StepVerifier.create(getTechnologiesByCapabilityUseCase.getTechnologies(capabilityId))
            .verifyComplete();

        verify(technologyRepository).findAllByCapabilityId(capabilityId);
    }
}
