package ru.practicum.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
