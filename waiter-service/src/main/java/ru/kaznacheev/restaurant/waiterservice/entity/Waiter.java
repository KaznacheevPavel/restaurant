package ru.kaznacheev.restaurant.waiterservice.entity;

import java.time.OffsetDateTime;

/**
 * Сущность официанта.
 */
public class Waiter {

    /**
     * Идентификатор официанта.
     */
    private Long id;

    /**
     * Имя официанта.
     */
    private String name;

    /**
     * Дата и время приема на работу.
     */
    private OffsetDateTime employedAt;

    /**
     * Пол официанта.
     */
    private Gender sex;
}
