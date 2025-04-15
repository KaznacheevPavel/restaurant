package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

/**
 * Сущность блюда.
 */
@Entity
@Table(name = "dish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    /**
     * Позиции заказа.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    private List<OrderPosition> orderPositions;

    /**
     * Сравнивает два блюда.
     *
     * @param o Объект для сравнения.
     * @return {@code true} если объекты равны, {@code false} в противном случае
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
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id);
    }

    /**
     * Возвращает хэш-код блюда.
     *
     * @return Хэш-код
     */
    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
