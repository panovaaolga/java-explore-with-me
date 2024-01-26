package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.SortOption;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class PrivateSubscriptionsController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/{subId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createSubscription(@PathVariable long userId, @PathVariable long subId) {
        log.info("userId = {}, subId = {}", userId, subId);
        return subscriptionService.createSubscription(userId, subId);
    }

    @DeleteMapping("/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable long userId, @PathVariable long subId) {
        log.info("userId = {}, subId = {}", userId, subId);
        subscriptionService.deleteSubscription(userId, subId);
    }

    @GetMapping("/subscribers")
    public List<UserShortDto> getSubscribers(@PathVariable long userId) {
        log.info("userId = {}", userId);
        return subscriptionService.getSubscribers(userId);
    }

    @GetMapping
    public List<UserShortDto> getSubscriptions(@PathVariable long userId) {
        log.info("userId = {}", userId);
        return subscriptionService.getSubscriptions(userId);
    }

    @GetMapping("/{subId}/events")
    public List<EventShortDto> getEventsBySubscription(@PathVariable long userId, @PathVariable long subId,
                                                       @RequestParam(defaultValue = "EVENT_DATE") SortOption sortOption,
                                                       @RequestParam(defaultValue = "false") boolean onlyUpcoming,
                                                       @RequestParam(defaultValue = "0") int from,
                                                       @RequestParam(defaultValue = "10") int size) {
        log.info("userId = {}, subId = {}, sortOption = {}, only = {}, from = {}, size = {}", userId, subId,
                sortOption, onlyUpcoming, from, size);
        return subscriptionService.getEventsBySubscription(userId, subId, sortOption, onlyUpcoming, from, size);
    }
}
