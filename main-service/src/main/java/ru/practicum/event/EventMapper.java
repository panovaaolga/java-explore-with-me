package ru.practicum.event;

import ru.practicum.category.CategoryMapper;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.user.User;

import java.time.LocalDateTime;

public class EventMapper {
    public static EventShortDto mapToShortEvent(Event event) {
        return null;
    }

    public static EventFullDto mapToFullEvent(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());

        return null;
    }

    public static Event mapToEvent(NewEventDto newEventDto, User initiator) {
        Event event = new Event();
        event.setCreatedOn(LocalDateTime.now());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(CategoryMapper.mapToCategory(newEventDto.getCategory()));
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setState(EventState.PENDING);
        event.setInitiator(initiator);
        return event;
    }
}
