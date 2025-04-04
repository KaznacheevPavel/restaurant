package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

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
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

}
