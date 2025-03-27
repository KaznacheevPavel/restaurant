package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс для определения названий и статус кодов ответов.
 */
@AllArgsConstructor
@Getter
public enum ResponseTitle {
    SUCCESS(200),
    CREATED(201),
    NOT_FOUND(404);

    /**
     * Статус код ответа.
     */
    private final int status;
}
