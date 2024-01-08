package ru.practicum.category.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @PostMapping
    public CategoryDto createCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return null;
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {

    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable long catId,
                                      @RequestBody CategoryDto categoryDto) {
        return null;
    }
}
