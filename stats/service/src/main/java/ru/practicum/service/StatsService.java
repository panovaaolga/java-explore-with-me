package ru.practicum.service;

import ru.practicum.dto.StatsDtoInput;
import ru.practicum.dto.StatsDtoOutput;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<StatsDtoOutput> getStats(LocalDateTime start, LocalDateTime end, String[] uris,
                                  boolean unique, int from, int size);

    void addHit(StatsDtoInput statsDtoInput);
}
