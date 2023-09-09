package ru.practicum.ewm.main.category.service;

import ru.practicum.ewm.main.category.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category);

    void deleteById(Long catId);

    List<Category> findAll(Integer from, Integer size);

    Category findById(Long catId);
}
