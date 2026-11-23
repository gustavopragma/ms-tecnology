package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.model.Technology;
import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterTechnologyUseCaseTest {

    @Mock
    private TechnologyRepository technologyRepository;

    private RegisterTechnologyUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterTechnologyUseCase(technologyRepository);
    }

    @Test
    void registerTechnologySuccess() {
        Technology request = new Technology(null, "Java", "Object programming language");
        Technology saved = new Technology(1L, "Java", "Object programming language");

        when(technologyRepository.existsByName("Java")).thenReturn(Mono.just(false));
        when(technologyRepository.save(any(Technology.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(useCase.registerTechnology(request))
            .expectNextMatches(t -> t.getId().equals(1L) && t.getName().equals("Java"))
            .verifyComplete();

        verify(technologyRepository).save(any(Technology.class));
    }

    @Test
    void registerTechnologyFailsWhenNameAlreadyExists() {
        Technology request = new Technology(null, "Java", "Valid description");

        when(technologyRepository.existsByName("Java")).thenReturn(Mono.just(true));

        StepVerifier.create(useCase.registerTechnology(request))
            .expectErrorMatches(ex -> ex instanceof BusinessException &&
                ((BusinessException) ex).getCode().equals("VE_001"))
            .verify();

        verify(technologyRepository, never()).save(any());
    }
}
