package ru.practicum.compilation.dto;

import lombok.Data;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CompilationDto {
    @NotNull(message = "Класс: CompilationDto. Поле: id. Причина: NotNull")
    private Long id;
    private boolean pinned;
    @NotBlank(message = "Класс: CompilationDto. Поле: title. Причина: NotBlank")
    private String title;
    private Set<EventShortDto> events;
}
