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
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.Objects;

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

    /**
     * Сравнивает два заказа.
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
        Order dish = (Order) o;
        return Objects.equals(id, dish.id);
    }

    /**
     * Возвращает хэш-код заказа.
     *
     * @return Хэш-код
     */
    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
