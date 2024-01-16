package ru.practicum.event.dto;

import ru.practicum.event.model.Status;

import java.util.List;

public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private Status status;
}
