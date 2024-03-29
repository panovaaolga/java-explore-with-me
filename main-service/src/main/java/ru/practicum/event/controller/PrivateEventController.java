package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsPrivate(@PathVariable long userId,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                               @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Private. Получение событий. userId = {}", userId);
        return eventService.getEventsPrivate(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId,
                                    @Validated @RequestBody NewEventDto newEventDto) {
        log.info("Private. Создание события. userId = {}, newEventDto: {}", userId, newEventDto);
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdPrivate(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Private. Получение события. eventId = {}, userId = {}", eventId, userId);
        return eventService.getEventByIdPrivate(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventPrivate(@PathVariable long userId, @PathVariable long eventId,
                                    @RequestBody @Validated UpdateEventUserRequest updateEventUserRequest) {
        log.info("Private. Обновление события. eventId = {}, userId = {}, request: {}",
                eventId, userId, updateEventUserRequest);
        return eventService.updateEventPrivate(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Private. Получение события. eventId = {}, userId = {}", eventId, userId);
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatus(@PathVariable long userId, @PathVariable long eventId,
                                                       @RequestBody @Validated EventRequestStatusUpdateRequest updateRequest) {
        log.info("Private. Обновление статуса. eventId = {}, userId = {}, request: {}",
                eventId, userId, updateRequest);
        return eventService.updateStatus(userId, eventId, updateRequest);
    }
}
