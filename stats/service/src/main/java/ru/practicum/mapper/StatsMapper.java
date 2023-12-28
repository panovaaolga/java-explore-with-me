package ru.practicum.mapper;

import ru.practicum.dto.Stats;
import ru.practicum.dto.StatsDtoInput;

public class StatsMapper {

    public static Stats mapToNewStats(StatsDtoInput statsDtoInput) {
        Stats stats = new Stats();
        stats.setApp(statsDtoInput.getApp());
        stats.setIp(statsDtoInput.getIp());
        stats.setUri(statsDtoInput.getUri());
        stats.setVisited(statsDtoInput.getVisited());
        return stats;
    }
}
