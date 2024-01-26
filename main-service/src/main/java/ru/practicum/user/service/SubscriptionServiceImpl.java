package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.SortOption;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.exceptions.UnsupportedActionException;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final EventService eventService;
    private final UserService userService;


    @Override
    @Transactional
    public UserDto createSubscription(long userId, long subscriptionId) {
        if (userId == subscriptionId) {
            throw new UnsupportedActionException(SubscriptionServiceImpl.class.getName(), "Нельзя подписаться на себя");
        }
        if (isSubscriber(userId, subscriptionId)) {
            throw new UnsupportedActionException(SubscriptionServiceImpl.class.getName(), "Нельзя подписаться повторно");
        }
        User user = userService.getUserById(userId);
        User userToSubscribe = userService.getUserById(subscriptionId);

        user.getSubscriptions().add(userToSubscribe);
        userToSubscribe.getSubscribers().add(user);

        userService.updateUser(userToSubscribe);
        return UserMapper.mapToUserDto(userService.updateUser(user));
    }

    @Override
    @Transactional
    public void deleteSubscription(long userId, long subscriptionId) {
        if (isSubscriber(userId, subscriptionId)) {
            User subscriber = userService.getUserById(userId);
            User subscription = userService.getUserById(subscriptionId);
            subscriber.getSubscriptions().remove(subscription);
            subscription.getSubscribers().remove(subscriber);
            userService.updateUser(subscriber);
            userService.updateUser(subscription);
        } else {
            throw new NotFoundException(User.class.getName(), subscriptionId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserShortDto> getSubscribers(long userId) {
        List<UserShortDto> userDtos = new ArrayList<>();
        Set<User> subscribers = userService.getUserById(userId).getSubscribers();
        if (!subscribers.isEmpty()) {
            for (User u : subscribers) {
                userDtos.add(UserMapper.mapToUserShort(u));
            }
        }
        return userDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserShortDto> getSubscriptions(long userId) {
        List<UserShortDto> userDtos = new ArrayList<>();
        Set<User> subscriptions = userService.getUserById(userId).getSubscriptions();
        if (!subscriptions.isEmpty()) {
            for (User u : subscriptions) {
                userDtos.add(UserMapper.mapToUserShort(u));
            }
        }
        return userDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsBySubscription(long userId, long subscriptionId, SortOption sortOption,
                                                       boolean onlyUpcoming, int from, int size) {
        if (!isSubscriber(userId, subscriptionId)) {
            log.info("exception thrown");
            throw new NotFoundException(User.class.getName(), subscriptionId);
        }
        List<EventShortDto> subscriptionEvents = eventService.getEventsPrivate(subscriptionId, from, size);
        log.info("Friends events: {}", subscriptionEvents);
        if (!subscriptionEvents.isEmpty()) {
            if (onlyUpcoming) {
                subscriptionEvents = subscriptionEvents.stream()
                        .filter(eventShortDto -> eventShortDto.getEventDate().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            }
            switch (sortOption) {
                case EVENT_DATE:
                    return subscriptionEvents.stream()
                            .sorted(Comparator.comparing(EventShortDto::getEventDate))
                            .collect(Collectors.toList());
                case VIEWS:
                    return subscriptionEvents.stream()
                            .sorted(Comparator.comparing(EventShortDto::getViews).reversed())
                            .collect(Collectors.toList());
            }
        }
        return subscriptionEvents;
    }

    private boolean isSubscriber(long subscriberId, long subscriptionId) {
        User subscription = userService.getUserById(subscriptionId);
        User subscriber = userService.getUserById(subscriberId);
        if (subscription.getSubscribers().contains(subscriber) && subscriber.getSubscriptions().contains(subscription)) {
            return true;
        }
        return false;
    }
}
