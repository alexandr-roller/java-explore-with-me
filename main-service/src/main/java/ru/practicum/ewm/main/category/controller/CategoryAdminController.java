package ru.practicum.ewm.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CategoryAdminController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Post category");
        return categoryMapper.toDTO(
                categoryService.create(
                        categoryMapper.fromDTO(categoryDto)
                )
        );
    }

    @DeleteMapping(value = "/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        log.info("Delete category id={}", catId);
        categoryService.deleteById(catId);
    }

    @PatchMapping(value = "/{catId}")
    public CategoryDto update(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Long catId
    ) {
        log.info("Patch category id={}", catId);
        categoryDto.setId(catId);
        return categoryMapper.toDTO(
                categoryService.update(
                        categoryMapper.fromDTO(categoryDto)
                )
        );
    }
}
