package ru.practicum.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @Email
    @NotBlank
    private String email;
    private Long id;
    @NotBlank
    private String name;
}
