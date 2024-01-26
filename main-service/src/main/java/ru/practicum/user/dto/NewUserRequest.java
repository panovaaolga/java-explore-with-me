package ru.practicum.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {
    @Email(message = "Класс: NewUserRequest. Поле: email. Причина: Email")
    @NotBlank(message = "Класс: NewUserRequest. Поле: email. Причина: NotBlank")
    @Size(min = 6, max = 254, message = "Класс: NewUserRequest. Поле: email. Причина: Size")
    private String email;
    @NotBlank(message = "Класс: NewUserRequest. Поле: name. Причина: NotBlank")
    @Size(min = 2, max = 250, message = "Класс: NewUserRequest. Поле: name. Причина: Size")
    private String name;
}
