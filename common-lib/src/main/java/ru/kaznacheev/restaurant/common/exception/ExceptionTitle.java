package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Названия и статус-коды для исключений.
 */
@AllArgsConstructor
@Getter
public enum ExceptionTitle {

    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST),
    VALIDATION_EXCEPTION("VALIDATION_EXCEPTION", HttpStatus.BAD_REQUEST);

    private final String title;
    private final HttpStatus status;

}
