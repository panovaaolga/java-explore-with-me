package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.SortOption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventService {

    List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, SortOption sort, int from, int size,
                                        String ipAddr, String path);

    EventFullDto getEventByIdPublic(long eventId, String ipAddr, String uri);

    List<EventFullDto> getEventsAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    List<EventShortDto> getEventsPrivate(long userId, int from, int size);

    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEventByIdPrivate(long userId, long eventId);

    EventFullDto updateEventPrivate(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest);

    List<Event> getEventsById(List<Long> ids);

    Event getEventById(long eventId);

    Set<EventShortDto> getShortDtoSet(Set<Event> events);
}
