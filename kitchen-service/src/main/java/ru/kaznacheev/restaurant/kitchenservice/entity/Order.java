package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Сущность заказа.
 */
@Entity
@Table(name = "kitchen_order")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Order {

    /**
     * Идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kitchen_order_id")
    private Long id;

    /**
     * Идентификатор заказа у официанта.
     */
    @Column(name = "waiter_order_no")
    private Long waiterOrderId;

    /**
     * Статус заказа из {@link OrderStatus}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    /**
     * Дата и время создания заказа.
     */
    @Column(name = "create_dttm")
    private OffsetDateTime createdAt;

}
