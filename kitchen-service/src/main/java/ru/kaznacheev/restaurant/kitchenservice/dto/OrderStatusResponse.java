package ru.kaznacheev.restaurant.kitchenservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.entity.KitchenOrderStatus;

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
     * Статус заказа из {@link KitchenOrderStatus}.
     */
    private KitchenOrderStatus status;

}
