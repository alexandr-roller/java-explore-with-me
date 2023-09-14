package ru.practicum.ewm.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CategoryPublicController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> get(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get categories from={}, size={}", from, size);
        return categoryMapper.toDTOs(categoryService.findAll(from, size));
    }

    @GetMapping(value = "/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        log.info("Get category id={}", catId);
        return categoryMapper.toDTO(categoryService.findById(catId));
    }
}
