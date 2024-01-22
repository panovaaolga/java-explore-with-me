package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.event.EventMapper;
import ru.practicum.event.ParticipationRequestMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.*;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.ParticipationRepository;
import ru.practicum.exceptions.DateValidationException;
import ru.practicum.exceptions.ForbiddenEventConditionException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final StatsClient statsClient;
    private final ParticipationRepository participationRepository;

    private final static String APP_NAME = "explore-with-me";


    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                               SortOption sort, int from, int size, String ipAddr, String uri) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        if (rangeStart == null && rangeEnd == null) {
            rangeStart = LocalDateTime.now();
        }

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new DateValidationException();
        }

        List<Event> events = eventRepository.findAllByParamPublic(text, categories, paid, rangeStart, rangeEnd,
                EventState.PUBLISHED, PageRequest.of(from / size, size)).getContent();

        if (onlyAvailable) {
            events = events.stream().filter(event -> event.getParticipantLimit() == 0 ||
                    event.getParticipantLimit() > event.getConfirmedRequests()).collect(Collectors.toList());
        }
        EndpointHitDto endpointHitDto = addToStats(ipAddr, uri);
        statsClient.addHit(endpointHitDto);
        if (!events.isEmpty()) {
            for (Event e : events) {
                eventShortDtoList.add(EventMapper.mapToShortEvent(e, getViews(e)));
            }
            if (sort != null) {
                switch (sort) {
                    case VIEWS:
                        eventShortDtoList = eventShortDtoList.stream().sorted((o1, o2) -> o2.getViews()
                                .compareTo(o1.getViews())).collect(Collectors.toList());
                        break;
                    case EVENT_DATE:
                        eventShortDtoList = eventShortDtoList.stream().sorted((o1, o2) -> o2.getEventDate().compareTo(o1.getEventDate()))
                                .collect(Collectors.toList());
                        break;
                }
            }
        }
        return eventShortDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdPublic(long eventId, String ipAddr, String uri) {
        Event event = getEventById(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException(Event.class.getName(), eventId);
        }
        EndpointHitDto endpointHitDto = addToStats(ipAddr, uri);
        statsClient.addHit(endpointHitDto);
        long views = getViews(event);
        return EventMapper.mapToFullEvent(event, views);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        List<Event> events = eventRepository.findAllByParam(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of(from / size, size)).getContent();
        if (!events.isEmpty()) {
            for (Event e : events) {
                eventFullDtoList.add(EventMapper.mapToFullEvent(e, getViews(e)));
            }
        }
        return eventFullDtoList;
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = getEventById(eventId);
        if (updateEventAdminRequest.getStateAction() != null) {
            if (!event.getState().equals(EventState.PENDING)) {
                String action = "";
                switch (updateEventAdminRequest.getStateAction()) {
                    case PUBLISH_EVENT:
                        action = "Опубликовать";
                        break;
                    case REJECT_EVENT:
                        action = "Отклонить";
                        break;
                }
                throw new ForbiddenEventConditionException(action + " можно только события со статусом PENDING");
            }

            switch (updateEventAdminRequest.getStateAction()) {
                case REJECT_EVENT:
                    event.setState(EventState.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    LocalDateTime now = LocalDateTime.now();
                    if (updateEventAdminRequest.getEventDate() != null) {
                        if (now.plusHours(1).isAfter(updateEventAdminRequest.getEventDate())) {
                            throw new ForbiddenEventConditionException("Время начала события должно быть минимум " +
                                    "на час позже времени публикации");
                        }
                    } else {
                        if (now.plusHours(1).isAfter(event.getEventDate())) {
                            throw new ForbiddenEventConditionException("Время начала события должно быть минимум " +
                                    "на час позже времени публикации");
                        }
                    }
                    event.setPublishedOn(now);
                    event.setState(EventState.PUBLISHED);
                    break;
            }
        } else {
            if (event.getState().equals(EventState.CANCELED)) {
                throw  new ForbiddenEventConditionException("Нельзя изменить отмененное событие");
            }
            if (updateEventAdminRequest.getEventDate() != null) {
                if (event.getPublishedOn() != null && event.getPublishedOn().plusHours(1).isAfter(event.getEventDate())) {
                    throw new ForbiddenEventConditionException("Время начала события должно быть минимум " +
                            "на час позже времени публикации");
                }
                event.setEventDate(updateEventAdminRequest.getEventDate());
            }
        }

        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getCategory() != null) {
            Category category = categoryService.getCategoryById(updateEventAdminRequest.getCategory());
            event.setCategory(category);
        }
        if (updateEventAdminRequest.getLocation() != null) {
            event.setLocation(updateEventAdminRequest.getLocation());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        return EventMapper.mapToFullEvent(eventRepository.save(event), getViews(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsPrivate(long userId, int from, int size) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        List<Event> events = eventRepository.findByInitiatorId(userId,
                PageRequest.of(from / size, size)).getContent();
        if (!events.isEmpty()) {
            for (Event e : events) {
                eventShortDtoList.add(EventMapper.mapToShortEvent(e, getViews(e)));
            }
        }
        return eventShortDtoList;
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isBefore(newEventDto.getEventDate())) {
            User initiator = userService.getUserById(userId);
            Category category = categoryService.getCategoryById(newEventDto.getCategory());
            return EventMapper.mapToFullEvent(eventRepository
                    .save(EventMapper.mapToEvent(newEventDto, initiator, category)), 0);
        }
        throw new ForbiddenEventConditionException("До события не может быть менее 2-х часов");
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdPrivate(long userId, long eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId));
        return EventMapper.mapToFullEvent(event, getViews(event));
    }

    @Override
    @Transactional
    public EventFullDto updateEventPrivate(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        log.info("action: {}", updateEventUserRequest.getStateAction());
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId));
        log.info("state 1: {}", event.getState());
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenEventConditionException("Нельзя изменить уже опубликованное событие");
        }
        if (updateEventUserRequest.getStateAction() != null) {
            switch (updateEventUserRequest.getStateAction()) {
                case CANCEL_REVIEW:
                    if (event.getState().equals(EventState.CANCELED)) {
                        throw new ForbiddenEventConditionException("Событие уже отменено");
                    } else {
                        event.setState(EventState.CANCELED);
                    }
                    break;
                case SEND_TO_REVIEW:
                    if (event.getState().equals(EventState.PENDING)) {
                        throw new ForbiddenEventConditionException("Событие уже находится на рассмотрении");
                    } else {
                        event.setState(EventState.PENDING);
                    }
                    break;
            }
        }
        LocalDateTime now = LocalDateTime.now();
        if (updateEventUserRequest.getEventDate() != null) {
            log.info("new event date: {}", updateEventUserRequest.getEventDate());
            if (now.plusHours(2).isAfter(updateEventUserRequest.getEventDate())) {
                throw new ForbiddenEventConditionException("До события не может быть менее 2-х часов");
            }
            event.setEventDate(updateEventUserRequest.getEventDate());
        } else {
            log.info("eventDate: {}", event.getEventDate());
            if (now.plusHours(2).isAfter(event.getEventDate())) {
                throw new ForbiddenEventConditionException("Нельзя изменить событие, " +
                        "до которого осталось менее 2-х часов");
            }
        }
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            Category category = categoryService.getCategoryById(updateEventUserRequest.getCategory());
            event.setCategory(category);
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }

        return EventMapper.mapToFullEvent(eventRepository.save(event), getViews(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        List<ParticipationRequest> participationRequests = participationRepository
                .findByEventIdAndEventInitiatorId(eventId, userId);
        return ParticipationRequestMapper.mapToDtoList(participationRequests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest updateRequest) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId));
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ForbiddenEventConditionException("Для события не требуется подтверждение заявок " +
                    "или не установлен лимит участников");
        }
        List<ParticipationRequest> participants = participationRepository
                .findByIdInOrderById(updateRequest.getRequestIds());
        log.info("participants: {}", participants);

        for (ParticipationRequest p : participants) {
            if (!p.getStatus().equals(ParticipationStatus.PENDING)) {
                throw new ForbiddenEventConditionException("Заявки должны находиться в статусе ожидания модерации");
            }
        }

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(new ArrayList<>());
        result.setRejectedRequests(new ArrayList<>());
        switch (updateRequest.getStatus()) {
            case CONFIRMED:
                int limit = event.getParticipantLimit();
                int confirmedRequests = event.getConfirmedRequests();
                if (limit == confirmedRequests) {
                    throw new ForbiddenEventConditionException("Лимит доступных заявок для одобрения исчерпан");
                }

                if (participants.size() > limit - confirmedRequests) {
                    for (int i = 0; i < participants.size(); i++) {
                        ParticipationRequest participant = participants.get(i);
                        if (limit - confirmedRequests > 0) {
                            participant.setStatus(ParticipationStatus.CONFIRMED);
                            participationRepository.save(participant);
                            result.getConfirmedRequests().add(ParticipationRequestMapper.mapToDto(participant));
                            confirmedRequests++;
                        } else {
                            participant.setStatus(ParticipationStatus.REJECTED);
                            participationRepository.save(participant);
                            result.getRejectedRequests().add(ParticipationRequestMapper.mapToDto(participant));
                        }
                    }

                    List<ParticipationRequest> pendingParticipants = participationRepository
                            .findByEventIdAndStatus(eventId, ParticipationStatus.PENDING);
                    for (int i = 0; i < pendingParticipants.size(); i++) {
                        ParticipationRequest participant = pendingParticipants.get(i);
                        participant.setStatus(ParticipationStatus.REJECTED);
                        participationRepository.save(participant);
                        result.getRejectedRequests().add(ParticipationRequestMapper.mapToDto(participant));
                    }
                } else {
                    for (int i = 0; i < participants.size(); i++) {
                        ParticipationRequest participant = participants.get(i);
                        participant.setStatus(ParticipationStatus.CONFIRMED);
                        participationRepository.save(participant);
                        confirmedRequests++;
                        result.getConfirmedRequests().add(ParticipationRequestMapper.mapToDto(participant));
                    }
                }
                event.setConfirmedRequests(confirmedRequests);
                eventRepository.save(event);
                break;
            case REJECTED:
                for (ParticipationRequest p : participants) {
                    p.setStatus(ParticipationStatus.REJECTED);
                    participationRepository.save(p);
                    result.getRejectedRequests().add(ParticipationRequestMapper.mapToDto(p));
                }
                break;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsById(List<Long> ids) {
        return eventRepository.findByIdIn(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEventById(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(Event.class.getName(), eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<EventShortDto> getShortDtoSet(Set<Event> events) {
        Set<EventShortDto> shortDtoSet = new HashSet<>();
        if (!events.isEmpty()) {
            for (Event e : events) {
                shortDtoSet.add(EventMapper.mapToShortEvent(e, getViews(e)));
            }
        }
        return shortDtoSet;
    }

    private long getViews(Event event) {
        long views = 0;
        if (event.getPublishedOn() != null) {
            List<ViewStats> viewStats = statsClient.getStats(event.getPublishedOn()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new String[]{"/events", "/events/" + event.getId()}, true, 0, 10);
            if (!viewStats.isEmpty()) {
                for (ViewStats v : viewStats) {
                    views = views + v.getHits();
                }
            }
        }
        log.info("views to return: {}", views);
        return views;
    }

    private EndpointHitDto addToStats(String ipAddr, String uri) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp(APP_NAME);
        endpointHitDto.setUri(uri);
        endpointHitDto.setIp(ipAddr);
        endpointHitDto.setVisited(LocalDateTime.now());
        return endpointHitDto;
    }
}
