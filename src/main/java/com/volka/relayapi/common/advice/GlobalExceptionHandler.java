package com.volka.relayapi.common.advice;

import com.volka.relayapi.common.constant.ResponseCode;
import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.common.exception.BizException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

/**
 * 컨트롤러 전역 예외 처리
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * TODO :: 예외코드 메시지 정의 후 수정
     * @return
     */
    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            ValidationException.class,
            MethodArgumentTypeMismatchException.class,
            ServerWebInputException.class
    })
    public Mono<ResponseEntity<StandardResponseDto<?>>> handleValidationException(Exception e, ServerWebExchange exchange) {
        log.error("[ERROR] :: ", e);
        if (e.getCause() != null) log.error("[ERROR][CAUSE] :: ", e);
        return Mono.just(ResponseEntity.badRequest()
                .body(createMessage(e.getMessage(), null, exchange)));
    }

    @ExceptionHandler(exception = WebExchangeBindException.class)
    public Mono<ResponseEntity<StandardResponseDto<?>>> handleBindException(WebExchangeBindException e, ServerWebExchange exchange) {
        log.error("[ERROR] :: ", e);
        if (e.getCause() != null) log.error("[ERROR][CAUSE] :: ", e.getCause());

        return Mono.just(ResponseEntity.badRequest()
                .body(createMessage(
                        ResponseCode.VALID_FAIL.code(),
                        e.getAllErrors().stream()
                                .map(ObjectError::getDefaultMessage)
                                .toArray(),
                        exchange
                ))
        );
    }

    @ExceptionHandler(exception = BizException.class)
    public Mono<ResponseEntity<StandardResponseDto<?>>> handleBizException(BizException e, ServerWebExchange exchange) {
        log.error("[ERROR] :: ", e);
        if (e.getCause() != null) log.error("[ERROR][CAUSE] :: ", e.getCause());

        return Mono.just(
                ResponseEntity.internalServerError()
                        .body(createMessage(e.getCode(), e.getExtraArgs(), exchange)));
    }

    @ExceptionHandler(exception = DataAccessException.class)
    public Mono<ResponseEntity<StandardResponseDto<?>>> handleBizException(DataAccessException e, ServerWebExchange exchange) {
        log.error("[ERROR] :: ", e);
        if (e.getCause() != null) log.error("[ERROR][CAUSE] :: ", e.getCause());

        return Mono.just(
                ResponseEntity.internalServerError()
                        .body(StandardResponseDto.fail(ResponseCode.FAIL)));
    }

    @ExceptionHandler(exception = Exception.class)
    public Mono<ResponseEntity<StandardResponseDto<?>>> handleException(Exception e) {
        log.error("[ERROR] :: ", e);
        if (e.getCause() != null) log.error("[ERROR][CAUSE] :: ", e.getCause());

        return Mono.just(
                ResponseEntity.internalServerError()
                        .body(StandardResponseDto.fail(ResponseCode.FAIL))
        );
    }

    private StandardResponseDto<?> createMessage(String errorCode, Object[] args, ServerWebExchange exchange) {
        return StandardResponseDto.fail(errorCode, messageSource.getMessage(errorCode, args, exchange.getLocaleContext().getLocale()));
    }
}
