package ru.practicum.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(String message, String reason, HttpStatus status) {
        this();
        this.message = message;
        this.status = status;
        this.reason = reason;
    }

}
