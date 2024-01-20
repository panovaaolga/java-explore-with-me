package ru.practicum.compilation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id", updatable = false)
    private Long id;
    @Column
    private boolean pinned;
    @Column(unique = true)
    private String title;
    @JoinTable
    @ManyToMany
    private Set<Event> events; //передаем полную сущность, которая связана с таблицей же?
}
