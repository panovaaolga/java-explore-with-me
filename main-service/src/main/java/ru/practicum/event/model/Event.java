package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import ru.practicum.category.Category;
import ru.practicum.compilation.Compilation;
import ru.practicum.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @Column(length = 2000)
    private String annotation;
    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Column(length = 7000)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @JoinColumn(referencedColumnName = "user_id", name = "initiator_id")
    @ManyToOne
    private User initiator;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "lat", referencedColumnName = "lat"),
            @JoinColumn(name = "lon", referencedColumnName = "lon")
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
    @Column(length = 120)
    private String title;
    @Column
    private long views; //как обновлять статистику?? Каждый раз после просмотра? Нам это здесь не надо вообще, кажется
    //Для дто можно подтягивать вызов статс клиент с получением статистики по запросу
    @JoinTable(name = "compilations_events", joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id"))
    @ManyToMany
    private Set<Compilation> compilations;
}
