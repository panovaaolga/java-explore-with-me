package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris,
                             boolean unique, int from, int size);

    void addHit(EndpointHitDto statsDtoInput);
}
