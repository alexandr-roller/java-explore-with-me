package ru.practicum.ewm.main.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.exception.CategoryNotFoundException;
import ru.practicum.ewm.main.category.repository.CategoryRepository;
import ru.practicum.ewm.main.category.service.CategoryService;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category categoryForUpdate = categoryRepository.findById(category.getId()).orElseThrow(() -> new CategoryNotFoundException(category.getId()));
        categoryForUpdate.setName(category.getName());

        return categoryRepository.save(categoryForUpdate);
    }

    @Override
    public void deleteById(Long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll(Integer from, Integer size) {
        return categoryRepository.findAllOrderById(EwmPageRequest.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId));
    }
}
