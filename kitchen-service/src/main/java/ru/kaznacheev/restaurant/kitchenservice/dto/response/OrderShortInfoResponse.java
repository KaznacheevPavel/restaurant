package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

import java.time.OffsetDateTime;

/**
 * DTO для ответа с краткой информацией о заказе.
 */
@Schema(description = "Краткая информация о заказе")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderShortInfoResponse {

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
     * Статус заказа из {@link OrderStatus}.
     */
    @Schema(description = "Статус заказа", example = "COMPLETED")
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    @Schema(description = "Дата и время создания заказа", example = "2025-01-03T12:54:00.000Z")
    private OffsetDateTime createdAt;

}
