package ru.kaznacheev.restaurant.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с информацией о позиции заказа.
 */
@Schema(description = "Информация о позиции заказа")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderPositionResponse {

    /**
     * Идентификатор блюда.
     */
    @Schema(description = "Идентификатор блюда", example = "2")
    private Long dishId;

    /**
     * Название блюда.
     */
    @Schema(description = "Название блюда", example = "Борщ")
    private String name;

    /**
     * Количество порций.
     */
    @Schema(description = "Количество порций", example = "1")
    private Long amount;

}
