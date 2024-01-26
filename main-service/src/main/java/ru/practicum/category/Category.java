package ru.practicum.category;

import lombok.Data;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false)
    private Long id;
    @Column(name = "category_name", unique = true)
    private String name;
    @OneToMany(mappedBy = "category")
    private Set<Event> events;
}
