package ru.practicum.ewm.main.user.exception;

import ru.practicum.ewm.main.common.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(id, "User");
    }
}
