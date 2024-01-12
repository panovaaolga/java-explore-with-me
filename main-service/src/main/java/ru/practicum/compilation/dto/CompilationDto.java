package ru.practicum.compilation.dto;

import ru.practicum.event.dto.EventShortDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "compilations")
public class CompilationDto {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id", updatable = false)
    private Long id;
    @Column
    private boolean pinned;
    @NotBlank
    @Column
    private String title;
    @JoinColumn(name = "event_id") //JoinTable?
    @ManyToMany
    private List<EventShortDto> events; //Set?
}
