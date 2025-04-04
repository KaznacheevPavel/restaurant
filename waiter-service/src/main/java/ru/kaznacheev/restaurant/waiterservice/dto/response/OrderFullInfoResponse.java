package ru.kaznacheev.restaurant.waiterservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO, содержащий полную информацию о заказе.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderFullInfoResponse {

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

    /**
     * Идентификатор официанта.
     */
    private Long waiterId;

    /**
     * Номер стола.
     */
    private String tableNumber;

    /**
     * Состав заказа.
     */
    private List<OrderPositionResponse> dishes;
}
