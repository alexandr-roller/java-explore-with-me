package ru.practicum.ewm.main.category.exception;

import ru.practicum.ewm.main.common.exception.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(Long id) {
        super(id, "Category");
    }
}
