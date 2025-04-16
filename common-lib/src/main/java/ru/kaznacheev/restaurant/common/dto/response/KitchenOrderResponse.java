package ru.kaznacheev.restaurant.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO для ответа с информацией о заказе на кухне.
 */
@Schema(description = "Информация о заказе")
@AllArgsConstructor
@Getter
@Builder
public class KitchenOrderResponse {

    /**
     * Идентификатор заказа.
     */
    @Schema(description = "Идентификатор заказа", example = "6")
    private Long id;

    /**
     * Идентификатор заказа у официантов.
     */
    @Schema(description = "Идентификатор заказа у официантов", example = "6")
    private Long waiterOrderId;

    /**
     * Статус заказа.
     */
    @Schema(description = "Статус заказа", example = "COMPLETED")
    private String status;

    /**
     * Дата и время создания заказа.
     */
    @Schema(description = "Дата и время создания заказа", example = "2025-01-03T12:54:00.000Z")
    private OffsetDateTime createdAt;

    /**
     * Состав заказа.
     */
    @Schema(description = "Состав заказа")
    private List<OrderPositionResponse> dishes;


}
