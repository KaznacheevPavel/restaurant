package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Базовое исключение.
 */
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{

    /**
     * Название ошибки.
     */
    private final String title;

    /**
     * Статус код ответа.
     */
    private final int status;

    /**
     * Детальное описание ошибки.
     */
    private final String detail;

}
