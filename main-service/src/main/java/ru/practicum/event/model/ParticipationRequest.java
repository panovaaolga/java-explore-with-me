package ru.practicum.event.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import ru.practicum.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;
    @Column
    private LocalDateTime created;
    @JoinColumn(referencedColumnName = "event_id")
    @ManyToOne
    private Event event;
    @JoinColumn(referencedColumnName = "user_id")
    @ManyToOne
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status; //default = PENDING
}
