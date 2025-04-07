package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

/**
 * DTO, содержащий статус заказа.
 */
@Getter
@Setter
public class OrderStatusResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

}
