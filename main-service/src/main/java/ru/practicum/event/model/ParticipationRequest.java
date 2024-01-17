package ru.practicum.event.model;

import org.springframework.boot.context.properties.bind.DefaultValue;
import ru.practicum.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;
    @Column
    private LocalDateTime created;
    @JoinColumn(referencedColumnName = "event_id")
    private Event event;
    @JoinColumn(referencedColumnName = "user_id")
    private User requester;
    @Column
    private ParticipationStatus status; //default = PENDING
}
