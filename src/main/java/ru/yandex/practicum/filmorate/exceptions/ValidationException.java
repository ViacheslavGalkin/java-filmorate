package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
