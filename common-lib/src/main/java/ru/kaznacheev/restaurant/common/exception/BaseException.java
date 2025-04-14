package ru.kaznacheev.restaurant.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Базовое исключение.
 */
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{

    /**
     * Подробное описание исключения.
     */
    private final String detail;

    /**
     * Дополнительная информация об исключении.
     * <p>
     * Ключ - название поля при сериализации в ответ, значение - значение поля.
     */
    private final Map<String, ?> data;

}
