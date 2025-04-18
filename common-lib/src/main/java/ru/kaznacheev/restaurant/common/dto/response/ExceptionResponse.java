package ru.kaznacheev.restaurant.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для ответа с информацией об исключении.
 */
@Schema(description = "Информация об исключении")
@AllArgsConstructor
@Getter
@Builder
public class ExceptionResponse {

    /**
     * Название исключения.
     */
    @Schema(description = "Название исключения", example = "NOT_FOUND")
    private final String title;

    /**
     * Статус-код исключения.
     */
    @Schema(description = "Статус-код ответа", example = "404")
    private final int status;

    /**
     * Подробное описание исключения.
     */
    @Schema(description = "Подробное описание исключения", example = "Заказ с идентификатором 25 не найден")
    private final String detail;

    /**
     * Дополнительная информация об исключении.
     * <p>
     * Ключ - название поля при сериализации в ответ, значение - значение поля.
     */
    @Schema(description = "Дополнительная информация об исключении")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, ?> data;

}
