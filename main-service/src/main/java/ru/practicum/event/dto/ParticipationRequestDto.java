package ru.practicum.event.dto;

import ru.practicum.event.model.Status;

import java.time.LocalDateTime;

public class ParticipationRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private Status status; //default = PENDING
}
