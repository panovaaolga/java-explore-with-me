package ru.practicum.repository;

import ru.practicum.dto.Stats;
import ru.practicum.dto.StatsDtoOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    @Query("select new ru.practicum.dto.StatsDtoOutput(s.app, s.uri, count(distinct s.ip)) " +
            "from ru.practicum.dto.Stats as s " +
            "where s.visited between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    Page<StatsDtoOutput> findUniqueHits(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("select new ru.practicum.dto.StatsDtoOutput(s.app, s.uri, count(distinct s.ip)) " +
            "from ru.practicum.dto.Stats as s " +
            "where s.uri in (?3) and " +
            "s.visited between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    Page<StatsDtoOutput> findUniqueHits(LocalDateTime start, LocalDateTime end, String[] uris, Pageable pageable);

    @Query("select new ru.practicum.dto.StatsDtoOutput(s.app, s.uri, count(s.id)) " +
            "from ru.practicum.dto.Stats as s " +
            "where s.visited between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(s.id) desc")
    Page<StatsDtoOutput> findNotUniqueHits(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("select new ru.practicum.dto.StatsDtoOutput(s.app, s.uri, count(s.id)) " +
            "from ru.practicum.dto.Stats as s " +
            "where s.uri in (?3) and " +
            "s.visited between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(s.id) desc")
    Page<StatsDtoOutput> findNotUniqueHits(LocalDateTime start, LocalDateTime end, String[] uris, Pageable pageable);
}
