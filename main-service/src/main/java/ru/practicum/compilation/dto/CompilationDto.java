package ru.practicum.compilation.dto;

import lombok.Data;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class CompilationDto {
    @NotNull(message = "Класс: CompilationDto. Поле: id. Причина: NotNull")
    private Long id;
    private boolean pinned;
    @NotBlank(message = "Класс: CompilationDto. Поле: title. Причина: NotBlank")
    @Size(min = 1, max = 50, message = "Класс: CompilationDto. Поле: title. Причина: Size")
    private String title;
    private Set<EventShortDto> events;
}
