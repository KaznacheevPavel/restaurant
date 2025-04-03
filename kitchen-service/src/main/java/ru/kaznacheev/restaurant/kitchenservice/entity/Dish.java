package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность блюда.
 */
@Entity
@Table(name = "dish")
@Getter
@Setter
public class Dish {

    /**
     * Идентификатор блюда.
     */
    @Id
    @Column(name = "dish_id")
    private Long id;

    /**
     * Доступное количество порций.
     */
    @Column(name = "balance")
    private Long balance;

    /**
     * Сокращенное название блюда.
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * Состав блюда.
     */
    @Column(name = "dish_composition")
    private String composition;

}
