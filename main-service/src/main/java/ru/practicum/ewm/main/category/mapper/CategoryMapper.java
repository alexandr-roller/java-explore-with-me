package ru.practicum.ewm.main.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.category.entity.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromDTO(CategoryDto dto);

    CategoryDto toDTO(Category category);

    List<CategoryDto> toDTOs(List<Category> categories);
}
