package ru.kaznacheev.restaurant.waiterservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Сущность официанта.
 */
@Getter
@Setter
@Builder
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
