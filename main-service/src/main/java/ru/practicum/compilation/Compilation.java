package ru.practicum.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id", updatable = false)
    private Long id;
    @Column
    private boolean pinned;
    @Column(unique = true)
    private String title;
    @JoinTable(name = "compilations_events", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ManyToMany
    private Set<Event> events;
}
