package ru.practicum.ewm.main.common.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends GlobalMainServiceException {
    public static final String REASON = "The required object was not found";

    public EntityNotFoundException(Long id, String model) {
        super(model + " with id=" + id + " was not found", HttpStatus.NOT_FOUND, REASON);
    }

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, REASON);
    }
}
