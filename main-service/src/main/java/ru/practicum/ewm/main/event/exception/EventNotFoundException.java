package ru.practicum.ewm.main.event.exception;

import ru.practicum.ewm.main.common.exception.EntityNotFoundException;

public class EventNotFoundException extends EntityNotFoundException {
    public EventNotFoundException(Long id) {
        super(id, "Event");
    }
}
