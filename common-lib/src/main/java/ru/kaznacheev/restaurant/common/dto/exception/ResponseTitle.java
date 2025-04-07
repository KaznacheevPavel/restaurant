package ru.kaznacheev.restaurant.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Названия и статус-коды ответов.
 */
@AllArgsConstructor
@Getter
public enum ResponseTitle {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    CONFLICT(HttpStatus.CONFLICT);

    /**
     * Статус код ответа.
     */
    private final HttpStatus status;
}
