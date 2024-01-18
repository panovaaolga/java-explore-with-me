package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "Класс: UserShortDto. Поле: id. Причина: NotNull")
    private Long id;
    @NotBlank(message = "Класс: UserShortDto. Поле: name. Причина: NotBlank")
    private String name;
}
