package ru.practicum.compilation.dto;

import ru.practicum.event.dto.EventShortDto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "compilations")
public class CompilationDto {
    @NotNull
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
    @JoinColumn //Many-To-One?
    private List<EventShortDto> events;
}
