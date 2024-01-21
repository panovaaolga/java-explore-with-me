package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.Location;

import javax.persistence.PrePersist;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @NotBlank(message = "Класс: NewEventDto. Поле: annotation. Причина: NotBlank")
    @Size(min = 20, max = 2000, message = "Класс: NewEventDto. Поле: annotation. Причина: Size")
    private String annotation;
    @NotNull(message = "Класс: NewEventDto. Поле: category. Причина: NotNull")
    private Long category;
    @NotBlank(message = "Класс: NewEventDto. Поле: description. Причина: NotBlank")
    @Size(min = 20, max = 7000, message = "Класс: NewEventDto. Поле: description. Причина: Size")
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    private boolean paid;
    private int participantLimit;
    private Boolean requestModeration; //default = true
    @NotBlank(message = "Класс: NewEventDto. Поле: title. Причина: NotBlank")
    @Size(min = 3, max = 120, message = "Класс: NewEventDto. Поле: title. Причина: Size")
    private String title;
}
