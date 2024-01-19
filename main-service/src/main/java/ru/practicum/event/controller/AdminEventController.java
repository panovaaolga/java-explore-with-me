package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events", consumes = "application/json", produces = "application/json")
public class AdminEventController {

    @GetMapping
    public List<EventFullDto> getEventsAdmin(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                        @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Admin. Получение событий. users = {}, states = {}, categories = {}, start = {}, end = {}",
                users, states, categories, rangeStart, rangeEnd);
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable long eventId,
                                    @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Admin. Обновление события. eventId = {}, request = {}", eventId, updateEventAdminRequest);
        return null;
    }
}
