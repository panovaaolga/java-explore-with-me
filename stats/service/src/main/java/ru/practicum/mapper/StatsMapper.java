package ru.practicum.mapper;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.EndpointHitDto;

public class StatsMapper {

    public static EndpointHit mapToNewStats(EndpointHitDto statsDtoInput) {
        EndpointHit stats = new EndpointHit();
        stats.setApp(statsDtoInput.getApp());
        stats.setIp(statsDtoInput.getIp());
        stats.setUri(statsDtoInput.getUri());
        stats.setVisited(statsDtoInput.getVisited());
        return stats;
    }
}
