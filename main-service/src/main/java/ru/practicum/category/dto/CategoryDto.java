package ru.practicum.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    private Long id;
    @NotBlank(message = "Класс: CategoryDto. Поле: name. Причина: NotBlank")
    @Size(min = 1, max = 50, message = "Класс: CategoryDto. Поле: name. Причина: Size")
    private String name;
}
