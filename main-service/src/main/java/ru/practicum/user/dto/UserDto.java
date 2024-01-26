package ru.practicum.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDto {
    @Email(message = "Класс: UserDto. Поле: email. Причина: Email")
    @NotBlank(message = "Класс: UserDto. Поле: email. Причина: NotBlank")
    @Size(min = 6, max = 254, message = "Класс: UserDto. Поле: email. Причина: Size")
    private String email;
    private Long id;
    @NotBlank(message = "Класс: UserDto. Поле: name. Причина: NotBlank")
    @Size(min = 2, max = 250, message = "Класс: UserDto. Поле: name. Причина: Size")
    private String name;
    private Set<UserShortDto> subscribers;
    private Set<UserShortDto> subscriptions;
}
