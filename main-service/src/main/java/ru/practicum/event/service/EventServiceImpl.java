package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.event.EventMapper;
import ru.practicum.event.ParticipationRequestMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.model.SortOption;
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
    private final StatsClient statsClient;
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
    public EventFullDto updateEventAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        //дата события должна быть min на час больше, чем дата публикации - иначе 409
        //изменить(отклон или опубл) можно только pending события - иначе 409
        return null;
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
        if (updateEventUserRequest.getAnnotation() != null &&
                !updateEventUserRequest.getAnnotation().equals(event.getAnnotation())) {
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
                .findByEventIdAndInitiatorId(eventId, userId);
        return ParticipationRequestMapper.mapToDtoList(participationRequests);
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest updateRequest) {

        //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие - 409
        //статус можно изменить только у заявок, находящихся в состоянии ожидания - 409
        //если при подтверждении данной заявки, лимит заявок для события исчерпан,
        // то все неподтверждённые заявки необходимо отклонить
        return null;
    }

    @Override
    public List<Event> getEventsById(List<Long> ids) {
        return eventRepository.findByIdIn(ids);
    }
}
