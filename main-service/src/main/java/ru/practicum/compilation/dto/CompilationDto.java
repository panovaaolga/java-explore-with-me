package ru.practicum.compilation.dto;

import lombok.Data;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CompilationDto {
    @NotNull
    private Long id;
    private boolean pinned;
    @NotBlank
    private String title;
    private Set<EventShortDto> events;
}
