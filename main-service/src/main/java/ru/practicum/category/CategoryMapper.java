package ru.practicum.category;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category mapToCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(category.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    public static List<CategoryDto> mapToDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        if (!categories.isEmpty()) {
            for (Category c : categories) {
                categoryDtos.add(mapToCategoryDto(c));
            }
        }
        return categoryDtos;
    }
}
