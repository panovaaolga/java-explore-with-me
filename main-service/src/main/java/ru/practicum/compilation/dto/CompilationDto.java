package ru.practicum.compilation.dto;

import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CompilationDto {
    @NotNull
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
    private List<EventShortDto> events;
}
