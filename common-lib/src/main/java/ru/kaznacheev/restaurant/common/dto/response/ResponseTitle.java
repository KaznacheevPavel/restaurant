package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Названия и статус-коды ответов.
 */
@AllArgsConstructor
@Getter
public enum ResponseTitle {
    SUCCESS(200),
    CREATED(201),
    VALIDATION_ERROR(400),
    NOT_FOUND(404),
    CONFLICT(409);

    /**
     * Статус код ответа.
     */
    private final int status;
}
