package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.SortOption;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/events", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsPublic(@RequestParam String text,
                                               @RequestParam List<Long> categories,
                                               @RequestParam Boolean paid,
                                               @RequestParam String rangeStart,
                                               @RequestParam String rangeEnd,
                                               @RequestParam boolean onlyAvailable,
                                               @RequestParam SortOption sort,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                               @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Public. Получение событий. text = {}, categories = {}, paid = {}, start = {}, end = {}, " +
                "onlyAvailable = {}, sort = {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdPublic(@PathVariable long eventId) {
        log.info("Public. Получение события. eventId = {}", eventId);
        return null;
    }

}
