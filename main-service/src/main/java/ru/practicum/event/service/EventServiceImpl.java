package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.client.StatsClient;
import ru.practicum.event.EventMapper;
import ru.practicum.event.ParticipationRequestMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.*;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.ParticipationRepository;
import ru.practicum.exceptions.ForbiddenEventConditionException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
   // private final StatsClient statsClient;
    private final ParticipationRepository participationRepository;


    @Override
    public List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart,
                                               String rangeEnd, boolean onlyAvailable, SortOption sort, int from,
                                               int size) {
        //только опубликованные события
        //поиск по тексту без регистра
        //если start and end == null, то выгружаем события позже now()
        //количество просмотров и одобренных заявок
        //сохраняем в сервис статистики
        return null;
    }

    @Override
    public EventFullDto getEventByIdPublic(long eventId) {
        //опубликованное событие
        //количество просмотров и одобренных заявок
        //сохраняем в сервис статистики
        return null;
    }

    @Override
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories,
                                             String rangeStart, String rangeEnd, int from, int size) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId));
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
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(EventState.PUBLISHED);
                    break;
            }
        }

        //дата != null и событие уже было опубликовано
        //дата != null и событие не опубликовано
        //дата == Null и событие опубликовано - тогда выбрасывать ничего не надо, значит, изменяются другие значения
        //дата == null и событие не опубликовано

        if (updateEventAdminRequest.getEventDate() != null) {

        } else {
            if (event.getPublishedOn().plusHours(1).isAfter(event.getEventDate())) {
                throw new ForbiddenEventConditionException("Нельзя опубликовать событие, " +
                        "до которого осталось менее 1 часа");
            }
        }



        //дата события должна быть min на час больше, чем дата публикации - иначе 409
        return EventMapper.mapToFullEvent(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getEventsPrivate(long userId, int from, int size) {
        return EventMapper.mapToShortEventList(eventRepository.findByInitiatorId(userId,
                PageRequest.of(from / size, size)).getContent());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isBefore(newEventDto.getEventDate())) {
            User initiator = userService.getUserById(userId);
            return EventMapper.mapToFullEvent(eventRepository.save(EventMapper.mapToEvent(newEventDto, initiator)));
        }
        throw new ForbiddenEventConditionException("До события не может быть менее 2-х часов");
    }

    @Override
    public EventFullDto getEventByIdPrivate(long userId, long eventId) {
        return EventMapper.mapToFullEvent(eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId)));
    }

    @Override
    @Transactional
    public EventFullDto updateEventPrivate(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(Event.class.getName(), eventId));
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenEventConditionException("Нельзя изменить уже опубликованное событие");
        }
        if (updateEventUserRequest.getEventDate() != null) {
            if (LocalDateTime.now().plusHours(2).isBefore(updateEventUserRequest.getEventDate())) {
                throw new ForbiddenEventConditionException("До события не может быть менее 2-х часов");
            }
            event.setEventDate(updateEventUserRequest.getEventDate());
        } else {
            if (LocalDateTime.now().plusHours(2).isBefore(event.getEventDate())) {
                throw new ForbiddenEventConditionException("Нельзя изменить событие, " +
                        "до которого осталось менее 2-х часов");
            }
        }
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            Category category = categoryService.getCategoryById(updateEventUserRequest.getCategory())
                    .orElseThrow(() -> new ForbiddenEventConditionException("Не существует категории с id = " +
                            updateEventUserRequest.getCategory()));
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

        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        return EventMapper.mapToFullEvent(eventRepository.save(event));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        List<ParticipationRequest> participationRequests = participationRepository
                .findByEventIdAndEventInitiatorId(eventId, userId);
        return ParticipationRequestMapper.mapToDtoList(participationRequests);
    }

    @Override
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
        for (ParticipationRequest p : participants) {
            if (!p.getStatus().equals(ParticipationStatus.PENDING)) {
                throw new ForbiddenEventConditionException("Заявки должны находиться в статусе ожидания модерации");
            }
        }

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
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
    public List<Event> getEventsById(List<Long> ids) {

        return eventRepository.findByIdIn(ids);
    }

    @Override
    public Event getEventById(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(Event.class.getName(), eventId));
    }
}
