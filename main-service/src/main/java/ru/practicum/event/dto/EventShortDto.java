package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class EventShortDto {
    @NotBlank
    @Size(min = 20, max = 2000, message = "Класс: EventShortDto. Поле: annotation. Причина: Size")
    private String annotation;
    @NotNull
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;
    private Long id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotBlank
    @Size(min = 3, max = 120, message = "Класс: EventShortDto. Поле: title. Причина: Size")
    private String title;
    private Long views;
}
