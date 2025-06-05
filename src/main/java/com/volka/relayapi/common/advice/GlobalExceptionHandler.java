package com.volka.relayapi.common.advice;

import com.volka.relayapi.common.constant.ResponseCode;
import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.common.exception.BizException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
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
    @ExceptionHandler(exception = {IllegalArgumentException.class, IllegalAccessException.class, BindException.class, ValidationException.class})
    public Mono<ResponseEntity<?>> handleValidation() {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardResponseDto.fail(ResponseCode.FAIL)));
    }

    @ExceptionHandler(exception = BizException.class)
    public Mono<ResponseEntity<?>> handleBizException(ServerWebExchange exchange, BizException e) {
        if (e.getCause() != null) log.error(e.getCause().getMessage());

        return Mono.just(
                ResponseEntity.internalServerError()
                        .body(StandardResponseDto.fail(e.getCode(), messageSource.getMessage(e.getCode(), e.getExtraArgs(), exchange.getLocaleContext().getLocale())))
        );
    }

    @ExceptionHandler(exception = Exception.class)
    public Mono<ResponseEntity<?>> handleException(Exception e) {
        return Mono.just(
                ResponseEntity.internalServerError()
                        .body(StandardResponseDto.fail(ResponseCode.FAIL))
        );
    }
}
