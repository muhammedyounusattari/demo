package com.ticket.viewers.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionTranslator {

    @ExceptionHandler(WebClientExampleGenericException.class) // fallback for all unhandled exceptions
    public Mono<ResponseEntity<?>> handleGenerateException(WebClientExampleGenericException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message","Something went wrong: " + ex.getMessage(),"status","404")));
    }

    @ExceptionHandler(RuntimeException.class) // fallback for all unhandled exceptions
    public Mono<ResponseEntity<?>> handleGenerateException(RuntimeException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message",ex.getMessage(),"status","500")));
    }
}
