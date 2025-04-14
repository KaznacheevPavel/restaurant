package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

import java.time.OffsetDateTime;

/**
 * DTO для ответа с краткой информацией о заказе.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderShortInfoResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Идентификатор заказа у официантов.
     */
    private Long waiterOrderId;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

}
