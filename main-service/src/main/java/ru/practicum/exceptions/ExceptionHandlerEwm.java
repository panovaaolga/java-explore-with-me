package ru.practicum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerEwm extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), ex.getCause().toString(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(error, error.getStatus());
    }

    @ExceptionHandler(ForbiddenEventConditionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleForbiddenConditions(ForbiddenEventConditionException ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Для данного события действуют ограничения, которые были нарушены");
        error.setStatus(HttpStatus.FORBIDDEN);
        return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
    }
}
