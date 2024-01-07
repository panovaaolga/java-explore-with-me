package ru.practicum.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserShortDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
