package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.model.ParticipationStatus;

import java.util.List;
import java.util.Set;

public interface ParticipationRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByEventIdAndInitiatorId(long eventId, long userId);

    List<ParticipationRequest> findByIdInOrderById(Set<Long> ids);

    int countByEventIdAndStatus(long eventId, ParticipationStatus status);

    List<ParticipationRequest> findByEventIdAndStatus(long eventId, ParticipationStatus status);
}
