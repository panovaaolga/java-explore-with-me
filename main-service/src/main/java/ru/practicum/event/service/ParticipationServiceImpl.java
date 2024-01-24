package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.ParticipationRequestMapper;
import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.ParticipationRequest;
import ru.practicum.event.model.ParticipationStatus;
import ru.practicum.event.repository.ParticipationRepository;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.exceptions.UnsupportedActionException;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
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
    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        if (participationRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new UnsupportedActionException(ParticipationServiceImpl.class.getName(), "Повторно подать заявку на участие невозможно");
        }
        Event event = eventService.getEventById(eventId);
        if (event.getInitiator().getId() == userId) {
            throw new UnsupportedActionException(ParticipationServiceImpl.class.getName(), "Нельзя подать заявку на участие в своем событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new UnsupportedActionException(ParticipationServiceImpl.class.getName(), "Нельзя подать заявку на участие в неопубликованном событии");
        }
        log.info("limit = {}, confirmed = {}", event.getParticipantLimit(), event.getConfirmedRequests());
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new UnsupportedActionException(ParticipationServiceImpl.class.getName(), "Лимит одобренных заявок на участие исчерпан");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(userService.getUserById(userId));
        request.setEvent(eventService.getEventById(eventId));
        request.setCreated(LocalDateTime.now());
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(ParticipationStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            request.setStatus(ParticipationStatus.PENDING);
        }
        return ParticipationRequestMapper.mapToDto(participationRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        ParticipationRequest request = participationRepository.findByRequesterIdAndId(userId, requestId)
                .orElseThrow(() -> new NotFoundException(ParticipationRequest.class.getName(), requestId));

        if (!request.getStatus().equals(ParticipationStatus.PENDING)) {
            throw new UnsupportedActionException(ParticipationServiceImpl.class.getName(), "Нельзя отозвать заявку на участие со статусом " +
                    request.getStatus());
        }
        request.setStatus(ParticipationStatus.CANCELED);
        return ParticipationRequestMapper.mapToDto(participationRepository.save(request));
    }
}
