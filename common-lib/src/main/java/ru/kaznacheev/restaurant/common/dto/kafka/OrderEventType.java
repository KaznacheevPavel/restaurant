package ru.kaznacheev.restaurant.common.dto.kafka;

/**
 * События заказа.
 */
public enum OrderEventType {

    /**
     * Заказ завершен.
     */
    COMPLETED,

    /**
     * Заказ отменен.
     */
    REJECTED;

}
