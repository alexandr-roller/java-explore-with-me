package ru.practicum.ewm.main.category.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.category.entity.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    void shouldMapFromDTO() {
        CategoryDto dto = CategoryDto.builder().id(1L).name("test").build();
        Category entity = categoryMapper.fromDTO(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    void shouldMapToDTO() {
        Category entity = Category.builder().id(1L).name("test").build();
        CategoryDto dto = categoryMapper.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }
}