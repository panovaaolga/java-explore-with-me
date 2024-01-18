package ru.practicum.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @Email(message = "Класс: UserDto. Поле: email. Причина: Email")
    @NotBlank(message = "Класс: UserDto. Поле: email. Причина: NotBlank")
    private String email;
    private Long id;
    @NotBlank(message = "Класс: UserDto. Поле: name. Причина: NotBlank")
    private String name;
}
