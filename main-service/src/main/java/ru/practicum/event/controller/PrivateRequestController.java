package ru.practicum.event.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.ParticipationRequestDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/requests")
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
