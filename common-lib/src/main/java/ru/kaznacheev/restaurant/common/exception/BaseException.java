package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Базовое исключение.
 */
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private final String title;
    private final HttpStatus status;
    private final String detail;
}
