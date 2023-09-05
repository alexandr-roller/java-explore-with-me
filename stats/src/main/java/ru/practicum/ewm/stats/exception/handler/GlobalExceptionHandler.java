package ru.practicum.ewm.stats.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Getter
    @Setter
    @Builder
    public static class ErrorResponse {
        @Builder.Default
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String error;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
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
