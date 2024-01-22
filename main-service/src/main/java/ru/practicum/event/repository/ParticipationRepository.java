package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.model.ParticipationStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByEventIdAndEventInitiatorId(long eventId, long userId);

    List<ParticipationRequest> findByIdInOrderById(Set<Long> ids);

    List<ParticipationRequest> findByEventIdAndStatus(long eventId, ParticipationStatus status);

    List<ParticipationRequest> findByRequesterId(long userId);

    Optional<ParticipationRequest> findByRequesterIdAndId(long userId, long id);

    Optional<ParticipationRequest> findByRequesterIdAndEventId(long userId, long eventId);
}
