package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByIdIn(List<Long> ids);

    Page<Event> findByInitiatorId(long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);
}
