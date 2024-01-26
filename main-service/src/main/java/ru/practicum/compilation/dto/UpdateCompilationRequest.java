package ru.practicum.compilation.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UpdateCompilationRequest {
    private Set<Long> events;
    private Boolean pinned;
    @Nullable
    @Size(min = 1, max = 50, message = "Класс: UpdateCompilationDto. Поле: title. Причина: Size")
    private String title;
}
