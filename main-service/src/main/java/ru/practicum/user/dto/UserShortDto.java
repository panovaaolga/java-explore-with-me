package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
