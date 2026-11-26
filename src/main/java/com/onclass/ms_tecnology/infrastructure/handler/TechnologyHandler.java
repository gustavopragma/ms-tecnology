package com.onclass.ms_tecnology.infrastructure.handler;

import com.onclass.ms_tecnology.application.usecase.AssociateCapabilityTechnologiesUseCase;
import com.onclass.ms_tecnology.application.usecase.DeleteTechnologiesByCapabilitiesUseCase;
import com.onclass.ms_tecnology.application.usecase.GetTechnologiesByCapabilityUseCase;
import com.onclass.ms_tecnology.application.usecase.RegisterTechnologyUseCase;
import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.AssociateCapabilityRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.DeleteTechnologiesByCapabilitiesRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.mapper.TechnologyDtoMapper;
import com.onclass.ms_tecnology.infrastructure.validation.ValidationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TechnologyHandler {
    private final RegisterTechnologyUseCase registerTechnologyUseCase;
    private final AssociateCapabilityTechnologiesUseCase associateCapabilityTechnologiesUseCase;
    private final GetTechnologiesByCapabilityUseCase getTechnologiesByCapabilityUseCase;
    private final DeleteTechnologiesByCapabilitiesUseCase deleteTechnologiesByCapabilitiesUseCase;
    private final TransactionalOperator transactionalOperator;
    private final TechnologyDtoMapper technologyDtoMapper;
    private final ValidationDto validationDto;

    public Mono<ServerResponse> register(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TechnologyRequestDto.class)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorCode.BODY_IS_REQUIRED)))
            .flatMap(validationDto::validate)
            .map(technologyDtoMapper::toDomain)
            .flatMap(registerTechnologyUseCase::registerTechnology)
            .map(technologyDtoMapper::toResponse)
            .flatMap(responseDto -> ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(responseDto));
    }

    public Mono<ServerResponse> associateCapability(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(AssociateCapabilityRequestDto.class)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorCode.BODY_IS_REQUIRED)))
            .flatMap(validationDto::validate)
            .flatMap(associateCapabilityRequestDto ->
                associateCapabilityTechnologiesUseCase.associate(
                    associateCapabilityRequestDto.getCapabilityId(),
                    associateCapabilityRequestDto.getTechnologyIds()
                ))
            .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    public Mono<ServerResponse> getByCapabilityId(ServerRequest serverRequest) {
        Long capabilityId = Long.valueOf(serverRequest.pathVariable("capabilityId"));
        return getTechnologiesByCapabilityUseCase.getTechnologies(capabilityId)
            .map(technologyDtoMapper::toResponse)
            .collectList()
            .flatMap(technologyResponseDtos ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(technologyResponseDtos)
            );
    }

    public Mono<ServerResponse> deleteByCapabilities(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DeleteTechnologiesByCapabilitiesRequestDto.class)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorCode.BODY_IS_REQUIRED)))
            .flatMap(validationDto::validate)
            .flatMap(requestDto ->
                deleteTechnologiesByCapabilitiesUseCase.deleteByCapabilityIds(requestDto.getCapabilityIds())
                    .as(transactionalOperator::transactional))
            .then(ServerResponse.noContent().build());
    }
}
