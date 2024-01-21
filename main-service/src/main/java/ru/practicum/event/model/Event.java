package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
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
    @ManyToMany(mappedBy = "events")
    private Set<Compilation> compilations;
}
