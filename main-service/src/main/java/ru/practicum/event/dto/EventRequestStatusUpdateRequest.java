package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.event.model.ParticipationStatus;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private Set<Long> requestIds;
    private ParticipationStatus status;
}
