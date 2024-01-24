package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "Класс: UserShortDto. Поле: id. Причина: NotNull")
    private Long id;
    @NotBlank(message = "Класс: UserShortDto. Поле: name. Причина: NotBlank")
    @Size(min = 2, max = 250, message = "Класс: UserShortDto. Поле: name. Причина: Size")
    private String name;
}
