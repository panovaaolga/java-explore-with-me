package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.event.model.ParticipationStatus;

import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private ParticipationStatus status;
}
