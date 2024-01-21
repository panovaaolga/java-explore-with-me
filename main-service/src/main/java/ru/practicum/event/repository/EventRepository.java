package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByIdIn(List<Long> ids);

    Page<Event> findByInitiatorId(long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

     Page<Event> findByAnnotationOrDescriptionContainingIgnoreCaseAndCategoryIdInAndPaidAndEventDateAfterAndEventDateBeforeAndState(String text,
                                                                                                                                    String textSecond, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, EventState state, Pageable pageable);


    Page<Event> findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(List<Long> users, List<EventState> states, List<Long> categories,
                       LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}
