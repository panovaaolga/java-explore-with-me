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
    @JoinColumn
    @ManyToOne
    private Category category;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Column
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @JoinColumn(referencedColumnName = "user_id", name = "initiator_id")
    @ManyToOne
    private User initiator;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "location_lat", referencedColumnName = "lat"),
            @JoinColumn(name = "location_lon", referencedColumnName = "lon")
    })
    private Location location;
    @Column
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "published")
    private LocalDateTime publishedOn;
    @Column(name = "moderation")
    private boolean requestModeration;
    @Column
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column
    private String title;
    @Column
    private long views; //как обновлять статистику?? Каждый раз после просмотра? Нам это здесь не надо вообще, кажется
    //Для дто можно подтягивать вызов статс клиент с получением статистики по запросу
    @JoinTable
    @ManyToMany
    private Set<Compilation> compilations; //дописать
}
