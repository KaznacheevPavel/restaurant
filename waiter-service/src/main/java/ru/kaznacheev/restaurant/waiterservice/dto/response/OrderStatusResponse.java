package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

/**
 * DTO, содержащий статус заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
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
