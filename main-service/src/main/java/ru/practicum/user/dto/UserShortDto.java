package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserShortDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
