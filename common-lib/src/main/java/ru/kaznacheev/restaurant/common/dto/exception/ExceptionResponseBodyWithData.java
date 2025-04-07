package ru.kaznacheev.restaurant.common.dto.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * Класс для тела ответа, содержащий дополнительные данные.
 */
@Getter
@SuperBuilder
public class ExceptionResponseBodyWithData extends BaseExceptionResponseBody {

    /**
     * Дополнительные данные.
     * <p>
     * Ключ - имя поля при сериализации в ответ, значение - содержимое поля
     */
    private final Map<String, ?> data;

}
