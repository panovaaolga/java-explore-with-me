package ru.practicum.category.service;

import ru.practicum.category.Category;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryDtoById(long catId);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    CategoryDto updateCategory(long catId, CategoryDto categoryDto);

    Category getCategoryById(long catId);
}
