package ru.kaznacheev.restaurant.kitchenservice.entity;

/**
 * Класс, представляющий статус заказа.
 */
public enum OrderStatus {
    WAITING_FOR_STARTING,
    IN_PROGRESS,
    COMPLETED,
    REJECTED
}
