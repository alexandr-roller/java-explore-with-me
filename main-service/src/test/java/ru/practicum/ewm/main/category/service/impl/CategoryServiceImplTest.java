package ru.practicum.ewm.main.category.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private final Category category = Category.builder().id(1L).name("name").build();

    @Test
    void shouldCreate() {
        when(categoryRepository.save(category)).thenReturn(category);

        assertEquals(category, categoryService.create(category));
    }

    @Test
    void shouldUpdate() {
        Category categoryForUpdate = Category.builder().id(1L).name("name").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        when(categoryRepository.save(any())).thenReturn(categoryForUpdate);

        assertEquals(categoryForUpdate, categoryService.update(categoryForUpdate));
    }

    @Test
    void deleteById() {
        long id = 1L;
        categoryService.deleteById(id);

        verify(categoryRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void findAll() {
        when(categoryRepository.findAllOrderById(any())).thenReturn(List.of(category));

        assertNotNull(categoryService.findAll(0, 10));
        verify(categoryRepository, Mockito.times(1)).findAllOrderById(any());
    }

    @Test
    void findById() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(category));

        assertEquals(category, categoryService.findById(id));
        verify(categoryRepository, Mockito.times(1)).findById(id);
    }
}