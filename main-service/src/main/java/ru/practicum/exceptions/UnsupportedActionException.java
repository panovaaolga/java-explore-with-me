package ru.practicum.exceptions;

public class UnsupportedActionException extends RuntimeException {

    public UnsupportedActionException(String className, String message) {

        super("В классе " + className + " произошла ошибка: " + message);
    }
}
