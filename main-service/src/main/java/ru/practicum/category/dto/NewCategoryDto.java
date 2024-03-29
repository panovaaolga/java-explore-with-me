package ru.practicum.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewCategoryDto {
    @NotBlank(message = "Класс: NewCategoryDto. Поле: name. Причина: NotBlank")
    @Size(min = 1, max = 50, message = "Класс: NewCategoryDto. Поле: name. Причина: Size")
    private String name;
}
