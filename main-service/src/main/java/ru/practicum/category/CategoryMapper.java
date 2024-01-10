package ru.practicum.category;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

public class CategoryMapper {

    public static CategoryDto mapToDto(NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(newCategoryDto.getName());
        return categoryDto;
    }
}
