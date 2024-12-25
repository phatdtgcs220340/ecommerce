package com.phatdo.ecommerce.arena.utils.exceptionhandler.controller;

import com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaException;
import com.phatdo.ecommerce.arena.utils.exceptionhandler.response.FailedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailedResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new FailedResponse("Form validation failed", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArenaException.class)
    public ResponseEntity<FailedResponse> customExceptionHandler(ArenaException exception) {
        log.error("ArenaException found : {}", exception.getMessage());
        return new ResponseEntity<>(new FailedResponse(exception.getMessage(), null), exception.getError().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedResponse> exceptionHandler(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        return ResponseEntity.internalServerError().body(new FailedResponse(exception.getMessage(), null));
    }
}
