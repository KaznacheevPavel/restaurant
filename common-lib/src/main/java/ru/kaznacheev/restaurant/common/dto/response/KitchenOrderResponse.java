package ru.kaznacheev.restaurant.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO для ответа с информацией о заказе на кухне.
 */
@AllArgsConstructor
@Getter
public class KitchenOrderResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Идентификатор заказа у официантов.
     */
    private Long waiterOrderId;

    /**
     * Статус заказа.
     */
    private String status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

    /**
     * Состав заказа.
     */
    private List<OrderPositionResponse> dishes;


}
