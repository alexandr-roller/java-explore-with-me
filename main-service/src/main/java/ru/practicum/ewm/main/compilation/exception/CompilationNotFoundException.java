package ru.practicum.ewm.main.compilation.exception;

import ru.practicum.ewm.main.common.exception.EntityNotFoundException;

public class CompilationNotFoundException extends EntityNotFoundException {
    public CompilationNotFoundException(Long id) {
        super(id, "Compilation");
    }
}
