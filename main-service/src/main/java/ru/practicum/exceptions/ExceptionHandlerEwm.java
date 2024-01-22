package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerEwm extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Некорректный запрос");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Некорректный запрос");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Выброшено исключение NotFoundException");
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getStatus());
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

    @ExceptionHandler({ConstraintViolationException.class, SQLException.class, HibernateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleForbiddenConditions(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Нарушены ограничения при обновлении данных в БД");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DateValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleIncorrectRequest(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setReason("Некорректный запрос");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
