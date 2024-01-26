package ru.practicum.user;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapper {

    public static User mapToUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        return user;
    }

    public static UserShortDto mapToUserShort(User user) {

        return new UserShortDto(user.getId(), user.getName(), user.getSubscribers().size(),
                user.getSubscriptions().size());
    }

    public static UserDto mapToUserDto(User user) {
        Set<UserShortDto> subscribers = new HashSet<>();
        Set<UserShortDto> subscriptions = new HashSet<>();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        if (!user.getSubscribers().isEmpty()) {
            for (User u : user.getSubscribers()) {
                subscribers.add(mapToUserShort(u));
            }
        }
        if (!user.getSubscriptions().isEmpty()) {
            for (User u : user.getSubscriptions()) {
                subscriptions.add(mapToUserShort(u));
            }
        }
        userDto.setSubscribers(subscribers);
        userDto.setSubscriptions(subscriptions);
        return userDto;
    }

    public static List<UserDto> mapToDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User u : users) {
                userDtos.add(mapToUserDto(u));
            }
        }
        return userDtos;
    }
}
