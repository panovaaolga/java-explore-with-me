package ru.practicum.compilation.dto;

import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewCompilationDto {
    private Boolean pinned; //default = false
    @NotBlank
    private String title;
    private List<EventShortDto> events;
}
