package com.onclass.ms_tecnology.infrastructure.handler;

import com.onclass.ms_tecnology.application.usecase.RegisterTechnologyUseCase;
import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.dto.TechnologyRequestDto;
import com.onclass.ms_tecnology.infrastructure.adapter.rest.mapper.TechnologyDtoMapper;
import com.onclass.ms_tecnology.infrastructure.validation.ValidationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TechnologyHandler {
    private final RegisterTechnologyUseCase registerTechnologyUseCase;
    private final TechnologyDtoMapper mapper;
    private final ValidationDto validationDto;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(TechnologyRequestDto.class)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorCode.BODY_IS_REQUIRED)))
            .flatMap(validationDto::validate)
            .map(mapper::toDomain)
            .flatMap(registerTechnologyUseCase::registerTechnology)
            .map(mapper::toResponse)
            .flatMap(responseDto -> ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(responseDto));
    }
}
