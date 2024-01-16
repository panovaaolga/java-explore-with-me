package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

@RestController
@RequestMapping(path = "/admin/categories", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Validated @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Admin. Создание новой категории: {}", newCategoryDto);
        return categoryService.createCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("Admin. Удаление категории с id = {}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable long catId,
                                      @Validated @RequestBody CategoryDto categoryDto) {
        log.info("Admin. Обновление категории с id = {}, новые данные = {}", catId, categoryDto);
        return categoryService.updateCategory(catId, categoryDto);
    }
}
