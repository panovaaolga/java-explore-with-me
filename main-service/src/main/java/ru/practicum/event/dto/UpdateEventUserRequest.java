package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.StateActionUser;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventUserRequest {
    @Nullable
    @Size(min = 20, max = 2000)
    private String annotation;
    @Size(min = 20, max = 7000)
    @Nullable
    private String description;
    private Long category;
    @Future
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionUser stateAction;
    @Size(min = 3, max = 120)
    @Nullable
    private String title;
}
