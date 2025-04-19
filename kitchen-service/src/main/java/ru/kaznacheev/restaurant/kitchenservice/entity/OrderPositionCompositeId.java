package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

/**
 * Составной идентификатор для позиции заказа.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
     * @return {@code true} если объекты равны, {@code false} в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        OrderPositionCompositeId orderPositionCompositeId = (OrderPositionCompositeId) o;
        return Objects.equals(orderId, orderPositionCompositeId.orderId)
                && Objects.equals(dishId, orderPositionCompositeId.dishId);
    }

    /**
     * Возвращает хэш-код составного ключа.
     *
     * @return Хэш-код
     */
    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
