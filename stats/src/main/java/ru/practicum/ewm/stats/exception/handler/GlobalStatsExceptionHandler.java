package ru.practicum.ewm.stats.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalStatsExceptionHandler {
    @Getter
    @Setter
    @Builder
    public static class ErrorResponse {
        @Builder.Default
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String error;
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.warn(ex.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse
                        .builder()
                        .status(badRequest.value())
                        .error("Validation error")
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        log.warn(ex.getMessage());
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse
                        .builder()
                        .status(badRequest.value())
                        .error("Internal server error")
                        .build());
    }
}
