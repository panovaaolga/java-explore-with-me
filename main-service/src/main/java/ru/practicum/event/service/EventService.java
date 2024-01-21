package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.SortOption;

import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart,
                                        String rangeEnd, boolean onlyAvailable, SortOption sort, int from, int size,
                                        String ipAddr, String path);

    EventFullDto getEventByIdPublic(long eventId, String ipAddr, String uri);

    List<EventFullDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                      String rangeEnd, int from, int size);

    EventFullDto updateEventAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    List<EventShortDto> getEventsPrivate(long userId, int from, int size);

    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEventByIdPrivate(long userId, long eventId);

    EventFullDto updateEventPrivate(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest);

    List<Event> getEventsById(List<Long> ids);

    Event getEventById(long eventId);
}
