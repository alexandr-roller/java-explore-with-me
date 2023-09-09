package ru.practicum.ewm.main.request.exception;

import ru.practicum.ewm.main.common.exception.EntityNotFoundException;

public class RequestNotFoundException extends EntityNotFoundException {
    public RequestNotFoundException(Long id) {
        super(id, "Request");
    }
}
