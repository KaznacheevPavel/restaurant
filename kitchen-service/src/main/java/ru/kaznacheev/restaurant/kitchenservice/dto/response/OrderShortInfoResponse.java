package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.entity.KitchenOrderStatus;

import java.time.OffsetDateTime;

/**
 * DTO, содержащий краткую информацию о заказе.
 */
@Getter
@Setter
public class OrderShortInfoResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Статус заказа из {@link KitchenOrderStatus}.
     */
    private KitchenOrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

}
