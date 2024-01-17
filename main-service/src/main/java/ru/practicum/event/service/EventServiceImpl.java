package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.event.EventMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.SortOption;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.ForbiddenEventConditionException;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final StatsClient statsClient;


    @Override
    public List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, boolean onlyAvailable, SortOption sort, int from, int size) {
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
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size) {
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
        return null;
    }

    @Override
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isBefore(newEventDto.getEventDate())) {
            User initiator = userService.getUserById(userId);
            return EventMapper.mapToFullEvent(eventRepository.save(EventMapper.mapToEvent(newEventDto, initiator)));
        }
        throw new ForbiddenEventConditionException("Дата и время, на которые намечено событие, не могут быть раньше, " +
                "чем через два часа от текущего момента");
    }

    @Override
    public EventFullDto getEventByIdPrivate(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventPrivate(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        //изменить можно только отмененные события или события в состоянии ожидания модерации - 409
        //min 2 часа до события на момент обновления - 409
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) {
        //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие - 409
        //статус можно изменить только у заявок, находящихся в состоянии ожидания - 409
        //если при подтверждении данной заявки, лимит заявок для события исчерпан,
        // то все неподтверждённые заявки необходимо отклонить
        return null;
    }
}
