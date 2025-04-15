package ru.kaznacheev.restaurant.kitchenservice.entity;

/**
 * Статусы заказа.
 */
public enum OrderStatus {

    /**
     * Заказ создан, но еще не начал готовиться.
     */
    NEW,

    /**
     * Заказ в процессе приготовления.
     */
    IN_PROGRESS,

    /**
     * Заказ приготовлен и завершен.
     */
    COMPLETED,

    /**
     * Заказ отклонен.
     */
    REJECTED
}
