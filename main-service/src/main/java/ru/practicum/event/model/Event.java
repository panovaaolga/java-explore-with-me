package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.Category;
import ru.practicum.compilation.Compilation;
import ru.practicum.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @Column
    private String annotation;
    @JoinColumn(referencedColumnName = "category_id")
    private Category category;
    @Column
    private Long confirmedRequests;
    @Column
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime eventDate;
    @JoinColumn(referencedColumnName = "user_id")
    private User initiator;

    private Location location; //как хранить данные о локации?
    @Column
    private Boolean paid;
    @Column
    private Integer participantLimit; //default = 0
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime publishedOn;
    @Column
    private Boolean requestModeration; //default = true
    @Column
    private State state;
    @Column
    private String title;
    @Column
    private Long views; //как обновлять статистику?? Каждый раз после просмотра?
    @JoinTable
    @ManyToMany
    private Set<Compilation> compilations; //дописать
}
