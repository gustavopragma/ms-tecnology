package com.onclass.ms_tecnology.infrastructure.exceptionHandler;

import com.onclass.ms_tecnology.domain.exception.BusinessException;
import com.onclass.ms_tecnology.domain.exception.ErrorCode;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(
        ErrorAttributes errorAttributes,
        WebProperties.Resources resources,
        ApplicationContext applicationContext,
        ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable ex = getError(request);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();

        if (ex instanceof BusinessException businessException) {
            status = HttpStatus.BAD_REQUEST;
            code = businessException.getCode();
            message = businessException.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            code,
            message
        );

        return ServerResponse.status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(errorResponse);
    }
}
