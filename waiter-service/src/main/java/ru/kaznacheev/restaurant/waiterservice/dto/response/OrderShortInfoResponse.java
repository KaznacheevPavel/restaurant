package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.Getter;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.time.OffsetDateTime;

/**
 * DTO, содержащий краткую информацию о заказе.
 */
@Getter
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
