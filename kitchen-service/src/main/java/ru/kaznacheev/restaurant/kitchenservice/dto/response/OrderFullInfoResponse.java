package ru.kaznacheev.restaurant.kitchenservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO, содержащий полную информацию о заказе.
 */
@Getter
@Setter
public class OrderFullInfoResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Идентификатор заказа у официанта.
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

    /**
     * Состав заказа.
     */
    private List<OrderPositionResponse> dishes;

}
