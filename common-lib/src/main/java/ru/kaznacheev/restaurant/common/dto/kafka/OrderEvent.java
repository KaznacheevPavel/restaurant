package ru.kaznacheev.restaurant.common.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * DTO, содержащее информацию о событии заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEvent {

    /**
     * Идентификатор заказа.
     */
    private Long orderId;

    /**
     * Тип события из {@link OrderEventType}.
     */
    private OrderEventType eventType;

    /**
     * Дата и время события.
     */
    private OffsetDateTime createdAt;

}
