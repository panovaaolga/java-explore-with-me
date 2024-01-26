package ru.practicum;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException() {
        super("Дата start не может быть позже end");
    }
}
