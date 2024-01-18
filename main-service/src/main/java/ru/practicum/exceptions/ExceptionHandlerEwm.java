package ru.practicum.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

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
        error.setReason("Нарушены ограничения, действующие для данного события");
        error.setStatus(HttpStatus.FORBIDDEN);
        return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleForbiddenConditions(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Нарушены ограничения при обновлении данных в БД");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIncorrectRequest(ValidationException ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason(ex.getCause().toString());
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
}
