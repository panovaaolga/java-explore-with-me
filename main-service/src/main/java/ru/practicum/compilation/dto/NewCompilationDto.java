package ru.practicum.compilation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class NewCompilationDto {
    private boolean pinned;
    @NotBlank(message = "Класс: NewCompilationDto. Поле: title. Причина: NotBlank")
    @Size(min = 1, max = 50, message = "Класс: NewCompilationDto. Поле: title. Причина: Size")
    private String title;
    private Set<Long> events;
}
