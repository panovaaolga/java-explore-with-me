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

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE ((UPPER(e.annotation) LIKE UPPER(:text) OR :text IS null) " +
            "OR (UPPER(e.description) LIKE UPPER(:text) OR :text IS null)) " +
            "AND (e.category.id IN :categories OR :categories IS null) " +
            "AND (e.paid = :paid OR :paid IS null) " +
            "AND (e.eventDate > :rangeStart OR CAST(:rangeStart AS LocalDateTime) IS null) " +
            "AND (e.eventDate < :rangeEnd OR CAST(:rangeEnd AS LocalDateTime) IS null) " +
            "AND e.state = :state")
     Page<Event> findAllByParamPublic(@Param("text") String text, @Param("categories") List<Long> categories,
                                      @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd, @Param("state") EventState state,
                                      Pageable pageable);



    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE (e.initiator.id IN :users OR :users IS null) " +
            "AND (e.state IN :states OR :states IS null) " +
            "AND (e.category.id IN :categories OR :categories IS null) " +
            "AND (e.eventDate > :start OR CAST(:start AS LocalDateTime) IS null) " +
            "AND (e.eventDate < :end OR CAST(:end AS LocalDateTime) IS null) ")
    Page<Event> findAllByParam(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime start,
                               LocalDateTime end, Pageable pageable);
}
