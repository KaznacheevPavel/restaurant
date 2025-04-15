package ru.kaznacheev.restaurant.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для ответа с информацией об исключении.
 */
@AllArgsConstructor
@Getter
@Builder
public class ExceptionResponse {

    /**
     * Название исключения.
     */
    private final String title;

    /**
     * Статус-код исключения.
     */
    private final int status;

    /**
     * Подробное описание исключения.
     */
    private final String detail;

    /**
     * Дополнительная информация об исключении.
     * <p>
     * Ключ - название поля при сериализации в ответ, значение - значение поля.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, ?> data;

}
