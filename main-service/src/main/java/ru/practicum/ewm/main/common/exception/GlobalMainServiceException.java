package ru.practicum.ewm.main.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class GlobalMainServiceException extends RuntimeException {
    @Getter
    private final HttpStatus status;
    @Getter
    private final String reason;

    public GlobalMainServiceException(String message, HttpStatus status, String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }
}
