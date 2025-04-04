package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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

    /**
     * Сравнивает две позиции заказов.
     *
     * @param o Объект для сравнения
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderPosition dish = (OrderPosition) o;
        return Objects.equals(id, dish.id);
    }

    /**
     * Возвращает хэш-код позиции заказа.
     *
     * @return Хэш-код
     */
    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
