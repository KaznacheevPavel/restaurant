package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

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
    @Schema(description = "Идентификатор заказа", example = "5")
    private Long id;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    @Schema(description = "Статус заказа", example = "IN_PROGRESS")
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    @Schema(description = "Дата и время создания заказа", example = "2025-02-04T11:14:52.000Z")
    private OffsetDateTime createdAt;

}
