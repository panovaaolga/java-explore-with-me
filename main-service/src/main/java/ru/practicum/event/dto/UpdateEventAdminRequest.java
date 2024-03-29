package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.StateActionAdmin;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {
    @Nullable
    @Size(min = 20, max = 2000)
    private String annotation;
    @Nullable
    @Size(min = 20, max = 7000)
    private String description;
    private Long category;
    @Nullable
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @Nullable
    private StateActionAdmin stateAction;
    @Size(min = 3, max = 120)
    @Nullable
    private String title;
}
