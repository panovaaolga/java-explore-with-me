package ru.practicum.exceptions;

public class DateValidationException extends RuntimeException {

    public DateValidationException() {
        super("Дата rangeStart не может быть позже rangeEnd");
    }
}
