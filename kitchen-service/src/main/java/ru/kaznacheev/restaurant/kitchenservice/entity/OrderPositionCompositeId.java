package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Составной ключ для позиции заказа.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderPositionCompositeId implements Serializable {

    /**
     * Идентификатор заказа.
     */
    @Column(name = "kitchen_order_id")
    private Long orderId;

    /**
     * Идентификатор блюда.
     */
    @Column(name = "dish_id")
    private Long dishId;

    /**
     * Сравнивает два составных ключа.
     *
     * @param o Объект для сравнения
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPositionCompositeId that = (OrderPositionCompositeId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(dishId, that.dishId);
    }

    /**
     * Возвращает хэш-код составного ключа.
     *
     * @return Хэш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishId);
    }
}
