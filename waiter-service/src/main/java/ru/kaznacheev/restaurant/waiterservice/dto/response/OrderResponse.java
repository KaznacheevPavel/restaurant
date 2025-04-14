package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

    /**
     * Идентификатор официанта.
     */
    private Long waiterId;

    /**
     * Номер стола.
     */
    private String tableNumber;

    /**
     * Стоимость заказа.
     */
    private BigDecimal cost;

    /**
     * Состав заказа.
     */
    private List<OrderPositionResponse> composition;

}
