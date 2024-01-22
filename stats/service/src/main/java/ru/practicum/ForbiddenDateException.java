package ru.practicum;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ForbiddenDateException extends RuntimeException {
    public ForbiddenDateException() {
        super("Дата start не может быть позже end");
    }
}
