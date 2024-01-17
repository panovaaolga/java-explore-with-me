package ru.practicum.exceptions;

public class ForbiddenEventConditionException extends RuntimeException {

    public ForbiddenEventConditionException(String message) {
        super(message);
    }
}
