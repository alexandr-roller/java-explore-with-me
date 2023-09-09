package ru.practicum.ewm.main.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewm.main.common.exception.GlobalMainServiceException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class MainServiceExceptionHandler {
    @Getter
    @Setter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorResponse {
        private String status;
        private String reason;
        private String message;
        @Builder.Default
        private LocalDateTime timestamp = LocalDateTime.now();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(GlobalMainServiceException ex) {
        log.warn(ex.getMessage());
        HttpStatus status = ex.getStatus();

        return ResponseEntity
                .status(status)
                .body(ErrorResponse
                        .builder()
                        .status(status.name())
                        .reason(ex.getReason())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex) {
        log.warn(ex.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity
                .status(status)
                .body(ErrorResponse
                        .builder()
                        .status(status.name())
                        .reason("Integrity constraint has been violated")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
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
                        .status(badRequest.name())
                        .reason("Incorrectly made request")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmptyResultDataAccessException ex) {
        log.warn(ex.getMessage());
        HttpStatus badRequest = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse
                        .builder()
                        .status(badRequest.name())
                        .reason("The required object was not found")
                        .message(ex.getMessage())
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
                        .status(badRequest.name())
                        .reason("Internal server error")
                        .message(ex.getMessage())
                        .build());
    }
}
