package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.SortOption;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsPublic(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                           LocalDateTime rangeStart,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                           LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(defaultValue = "EVENT_DATE") SortOption sort,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                               @Positive @RequestParam(defaultValue = "10") int size,
                                               HttpServletRequest request) {
        log.info("Public. Получение событий. text = {}, categories = {}, paid = {}, start = {}, end = {}, " +
                "onlyAvailable = {}, sort = {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
        return eventService.getEventsPublic(text,categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, request.getRemoteAddr(), request.getRequestURI());
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdPublic(@PathVariable long eventId, HttpServletRequest request) {
        log.info("Public. Получение события. eventId = {}, ip = {}, uri = {}", eventId, request.getRemoteAddr(),
                request.getRequestURI());
        return eventService.getEventByIdPublic(eventId, request.getRemoteAddr(), request.getRequestURI());
    }

}
