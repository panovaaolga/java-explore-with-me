package ru.practicum.event.dto;

import ru.practicum.event.model.ParticipationStatus;

import java.util.List;

public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private ParticipationStatus status;
}
