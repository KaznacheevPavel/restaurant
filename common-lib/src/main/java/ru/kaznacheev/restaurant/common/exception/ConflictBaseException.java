package ru.kaznacheev.restaurant.common.exception;

import java.util.Map;

/**
 * Исключение для CONFLICT (409).
 */
public class ConflictBaseException extends BaseException {

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     */
    public ConflictBaseException(String detail) {
        super(detail, Map.of());
    }

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     * @param data Дополнительная информация об исключении
     */
    public ConflictBaseException(String detail, Map<String, ?> data) {
        super(detail, data);
    }

}
