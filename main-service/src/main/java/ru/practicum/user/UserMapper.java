package ru.practicum.user;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User mapToUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        return user;
    }

    public static UserShortDto mapToUserShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
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
