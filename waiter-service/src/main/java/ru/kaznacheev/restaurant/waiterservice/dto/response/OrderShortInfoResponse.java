package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

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
     * Статус заказа из {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

}
