package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.event.model.ParticipationStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private Set<Long> requestIds;
    private ParticipationStatus status;
}
