package ru.practicum.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {
    @Email
    @NotBlank
    private String email;
    private Long id;
    @NotBlank
    private String name;
}
