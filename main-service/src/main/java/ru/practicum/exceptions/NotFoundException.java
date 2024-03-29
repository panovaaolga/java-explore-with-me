package ru.practicum.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className, long id) {
        super("Не удалось найти: " + className + " с id = " + id);
    }
}
