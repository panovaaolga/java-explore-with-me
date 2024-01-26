package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.IncorrectDateException;
import ru.practicum.dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.StatsMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.repository.StatsRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;


    @Transactional
    @Override
    public void addHit(EndpointHitDto statsDtoInput) {
        statsRepository.save(StatsMapper.mapToNewStats(statsDtoInput));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris,
                                    boolean unique, int from, int size) {
        if (start.isAfter(end)) {
            throw new IncorrectDateException();
        }
        if (unique) {
            if (uris == null) {
                return statsRepository.findUniqueHits(start, end, PageRequest.of(from / size, size))
                        .getContent();
            } else {
                return statsRepository.findUniqueHits(start, end, uris,
                        PageRequest.of(from / size, size)).getContent();
            }
        } else {
            if (uris == null) {
                return statsRepository.findNotUniqueHits(start, end, PageRequest.of(from / size, size))
                        .getContent();
            } else {
                return statsRepository.findNotUniqueHits(start, end, uris, PageRequest.of(from / size, size))
                        .getContent();
            }
        }
    }
}
