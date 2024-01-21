package ru.practicum.event.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    @Column
    private LocalDateTime created;
    @JoinColumn(name = "event_id")
    @ManyToOne
    private Event event;
    @JoinColumn(name = "requester_id")
    @ManyToOne
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;
}
