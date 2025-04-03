package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Сущность позиции заказа.
 */
@Entity
@Table(name = "order_to_dish")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPosition {

    /**
     * Идентификатор позиции заказа.
     */
    @EmbeddedId
    private OrderPositionCompositeId id;

    /**
     * Количество порций.
     */
    @Column(name = "dishes_number")
    private Long amount;

}
