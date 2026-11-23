package com.onclass.ms_tecnology.infrastructure.validation;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationDto {

    private final Validator validator;

    public <T> Mono<T> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                .map(constraint -> constraint.getPropertyPath() + ": " + constraint.getMessage())
                .collect(Collectors.joining(", "));
            return Mono.error(new BusinessException(ErrorCode.VALIDATION_ERROR, errors));
        }
        return Mono.just(dto);
    }
}
