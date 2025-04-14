package ru.kaznacheev.restaurant.common.exception;

import java.util.Map;

/**
 * Исключение для NOT_FOUND (404).
 */
public class NotFoundBaseException extends BaseException {

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     */
    public NotFoundBaseException(String detail) {
        super(detail, Map.of());
    }

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     * @param data Дополнительная информация об исключении
     */
    public NotFoundBaseException(String detail, Map<String, ?> data) {
        super(detail, data);
    }

}
