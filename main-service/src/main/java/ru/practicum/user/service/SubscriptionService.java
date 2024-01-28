package ru.practicum.user.service;

import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.SortOption;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;

public interface SubscriptionService {
    UserDto createSubscription(long userId, long subscriptionId);

    void deleteSubscription(long userId, long subscriptionId);

    List<UserShortDto> getSubscribers(long userId);

    List<UserShortDto> getSubscriptions(long userId);

    List<EventShortDto> getEventsBySubscription(long userId, long subscriptionId, SortOption sortOption,
                                                boolean onlyUpcoming, int from, int size);
}
