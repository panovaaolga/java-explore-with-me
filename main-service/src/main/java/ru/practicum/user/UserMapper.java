package ru.practicum.user;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

public class UserMapper {

    public static UserDto mapToUser(NewUserRequest newUserRequest) {
        UserDto userDto = new UserDto();
        userDto.setEmail(newUserRequest.getEmail());
        userDto.setName(newUserRequest.getName());
        return userDto;
    }

    public static UserShortDto mapToUserShort(UserDto userDto) {
        return new UserShortDto(userDto.getId(), userDto.getName());
    }
}
