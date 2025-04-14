package ru.kaznacheev.restaurant.common.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.restaurant.common.entity.KitchenOrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO, содержащий полную информацию о заказе.
 */
@Getter
@Setter
public class KitchenOrderFullInfoResponse {

    /**
     * Идентификатор заказа.
     */
    private Long id;

    /**
     * Идентификатор заказа у официанта.
     */
    private Long waiterOrderId;

    /**
     * Статус заказа из {@link KitchenOrderStatus}.
     */
    private KitchenOrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    private OffsetDateTime createdAt;

    /**
     * Состав заказа.
     */
    private List<OrderPositionResponse> dishes;

}
