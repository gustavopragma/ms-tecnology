package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTechnologiesByCapabilitiesUseCaseTest {

    @Mock
    private TechnologyRepository technologyRepository;

    @InjectMocks
    private DeleteTechnologiesByCapabilitiesUseCase deleteTechnologiesByCapabilitiesUseCase;

    @Test
    void deleteTechnologiesSuccess() {
        List<Long> capabilityIds = List.of(10L);

        when(technologyRepository.findTechnologyIdsByCapabilityIds(capabilityIds))
            .thenReturn(Flux.just(1L, 2L));
        when(technologyRepository.deleteAllByCapabilityIds(capabilityIds))
            .thenReturn(Mono.empty());
        when(technologyRepository.countByTechnologyId(1L)).thenReturn(Mono.just(0L));
        when(technologyRepository.countByTechnologyId(2L)).thenReturn(Mono.just(1L));
        when(technologyRepository.deleteAllById(List.of(1L))).thenReturn(Mono.empty());

        StepVerifier.create(deleteTechnologiesByCapabilitiesUseCase.deleteByCapabilityIds(capabilityIds))
            .verifyComplete();

        verify(technologyRepository).deleteAllById(List.of(1L));
    }

    @Test
    void noDeleteTechnologiesWhenHasOtherCapabilityAssociate() {
        List<Long> capabilityIds = List.of(10L);
        when(technologyRepository.findTechnologyIdsByCapabilityIds(capabilityIds))
            .thenReturn(Flux.just(2L));
        when(technologyRepository.deleteAllByCapabilityIds(capabilityIds))
            .thenReturn(Mono.empty());
        when(technologyRepository.countByTechnologyId(2L)).thenReturn(Mono.just(2L));

        StepVerifier.create(deleteTechnologiesByCapabilitiesUseCase.deleteByCapabilityIds(capabilityIds))
            .verifyComplete();

        verify(technologyRepository, never()).deleteAllById(anyList());
    }

    @Test
    void noDeleteWhenCapabilitiesIsEmpty() {
        StepVerifier.create(deleteTechnologiesByCapabilitiesUseCase.deleteByCapabilityIds(List.of()))
            .verifyComplete();

        verify(technologyRepository, never()).findTechnologyIdsByCapabilityIds(anyList());
    }
}
