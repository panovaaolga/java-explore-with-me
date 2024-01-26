package ru.practicum.event.service;

import ru.practicum.event.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationService {

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
