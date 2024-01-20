package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.ParticipationRequestMapper;
import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.model.ParticipationStatus;
import ru.practicum.event.repository.ParticipationRepository;
import ru.practicum.exceptions.ForbiddenEventConditionException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ParticipationServiceImpl implements ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequests(long userId) {
        userService.getUserById(userId);
        return ParticipationRequestMapper.mapToDtoList(participationRepository.findByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        if (participationRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ForbiddenEventConditionException("Повторно подать заявку на участие невозможно");
        }
        Event event = eventService.getEventById(eventId);
        if (event.getInitiator().getId() == userId) {
            throw new ForbiddenEventConditionException("Нельзя подать заявку на участие в своем событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenEventConditionException("Нельзя подать заявку на участие в неопубликованном событии");
        }
        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ForbiddenEventConditionException("Лимит одобренных заявок на участие исчерпан");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(userService.getUserById(userId));
        request.setEvent(eventService.getEventById(eventId));
        request.setCreated(LocalDateTime.now());
        if (!event.isRequestModeration()) {
            request.setStatus(ParticipationStatus.CONFIRMED);
        } else {
            request.setStatus(ParticipationStatus.PENDING);
        }
        return ParticipationRequestMapper.mapToDto(participationRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        ParticipationRequest request = participationRepository.findByRequesterIdAndEventId(userId, requestId)
                .orElseThrow(() -> new NotFoundException(ParticipationRequest.class.getName(), requestId));

        if (!request.getStatus().equals(ParticipationStatus.PENDING)) {
            throw new ForbiddenEventConditionException("Нельзя отозвать заявку на участие со статусом " +
                    request.getStatus());
        }
        request.setStatus(ParticipationStatus.CANCELED);
        return ParticipationRequestMapper.mapToDto(participationRepository.save(request));
    }
}
