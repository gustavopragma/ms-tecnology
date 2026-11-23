package com.onclass.ms_tecnology.application.usecase;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateCapabilityTechnologiesUseCaseTest {

    @Mock
    private TechnologyRepository technologyRepository;

    @InjectMocks
    private AssociateCapabilityTechnologiesUseCase associateCapabilityTechnologiesUseCase;

    @Test
    void associateTechnologiesSuccess() {
        Long capabilityId = 1L;
        List<Long> technologyIds = List.of(1L, 2L, 3L);

        when(technologyRepository.findExistingIds(Set.of(1L, 2L, 3L)))
            .thenReturn(Flux.just(1L, 2L, 3L));
        when(technologyRepository.associateTechnologiesToCapability(eq(capabilityId), anyList()))
            .thenReturn(Mono.empty());

        StepVerifier.create(associateCapabilityTechnologiesUseCase.associate(capabilityId, technologyIds))
            .verifyComplete();

        verify(technologyRepository).associateTechnologiesToCapability(eq(capabilityId), anyList());
    }

    @Test
    void associateTechnologiesSuccessWhenDuplicateIdsArePassed() {
        Long capabilityId = 1L;
        List<Long> duplicatedIds = List.of(1L, 1L, 2L, 3L);

        when(technologyRepository.findExistingIds(Set.of(1L, 2L, 3L)))
            .thenReturn(Flux.just(1L, 2L, 3L));
        when(technologyRepository.associateTechnologiesToCapability(eq(capabilityId), anyList()))
            .thenReturn(Mono.empty());

        StepVerifier.create(associateCapabilityTechnologiesUseCase.associate(capabilityId, duplicatedIds))
            .verifyComplete();

        verify(technologyRepository).findExistingIds(Set.of(1L, 2L, 3L));
    }

    @Test
    void failedAssociateWhenTechnologiesDoNotExist() {
        Long capabilityId = 1L;
        List<Long> technologyIds = List.of(1L, 2L, 99L);

        when(technologyRepository.findExistingIds(Set.of(1L, 2L, 99L)))
            .thenReturn(Flux.just(1L, 2L));

        StepVerifier.create(associateCapabilityTechnologiesUseCase.associate(capabilityId, technologyIds))
            .expectErrorSatisfies(throwable -> {
                assertThat(throwable).isInstanceOf(BusinessException.class);
                BusinessException ex = (BusinessException) throwable;
                assertThat(ex.getCode()).isEqualTo(ErrorCode.TECHNOLOGY_NOT_FOUND.getCode());
                assertThat(ex.getMessage()).contains("99");
            })
            .verify();

        verify(technologyRepository, never()).associateTechnologiesToCapability(eq(capabilityId), anyList());
    }
}
