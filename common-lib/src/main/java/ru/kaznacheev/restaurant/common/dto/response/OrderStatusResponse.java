package ru.kaznacheev.restaurant.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с информацией о статусе заказа.
 */
@Schema(description = "Информация о статусе заказа")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusResponse {

    /**
     * Идентификатор заказа.
     */
    @Schema(description = "Идентификатор заказа", example = "6")
    private Long id;

    /**
     * Статус заказа.
     */
    @Schema(description = "Статус заказа", example = "COMPLETED")
    private String status;

}
