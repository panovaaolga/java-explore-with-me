package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.ParticipationRequestDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "users/{userId}/requests", consumes = "application/json", produces = "application/json")
public class PrivateRequestController {

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId) {
        return null;
    }

    @PostMapping
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                 @NotNull @RequestParam long eventId) {
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        return null;
    }
}
