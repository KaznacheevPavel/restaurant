package ru.kaznacheev.restaurant.waiterservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO для ответа с информацией о заказе.
 */
@Schema(description = "Информация о заказе")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {

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

    /**
     * Идентификатор официанта.
     */
    @Schema(description = "Идентификатор официанта", example = "1")
    private Long waiterId;

    /**
     * Номер стола.
     */
    @Schema(description = "Номер стола", example = "3a")
    private String tableNumber;

    /**
     * Стоимость заказа.
     */
    @Schema(description = "Стоимость заказа", example = "1920")
    private BigDecimal cost;

    /**
     * Состав заказа.
     */
    @Schema(description = "Состав заказа")
    private List<OrderPositionResponse> composition;

}
