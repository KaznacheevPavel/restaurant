package ru.kaznacheev.restaurant.waiterservice.entity;

/**
 * Статусы заказа.
 */
public enum OrderStatus {

    /**
     * Заказ создан, но еще не принят на кухне.
     */
    NEW,

    /**
     * Заказ в процессе приготовления.
     */
    IN_PROGRESS,

    /**
     * Заказ приготовлен и ожидает оплаты.
     */
    COOKED,

    /**
     * Заказ оплачен и завершен.
     */
    COMPLETED,

    /**
     * Заказ отменен.
     */
    REJECTED

}
