package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.Getter;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

/**
 * DTO, содержащий статус заказа.
 */
@Getter
public class OrderStatusResponse {

    /**
     * Идентификатор заказа.
     */
    private Long orderId;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

}
