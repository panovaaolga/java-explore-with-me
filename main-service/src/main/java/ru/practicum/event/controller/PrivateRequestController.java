package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.service.ParticipationService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "users/{userId}/requests")
public class PrivateRequestController {

    private final ParticipationService participationService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId) {
        log.info("Private Request. Получение запроса. userId = {}", userId);
        return participationService.getRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                 @RequestParam long eventId) {
        log.info("Private Request. Создание запроса. userId = {}, eventId = {}", userId, eventId);
        return participationService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        log.info("Private Request. Отмена запроса. userId = {}, requestId = {}", userId, requestId);
        return participationService.cancelRequest(userId, requestId);
    }
}
