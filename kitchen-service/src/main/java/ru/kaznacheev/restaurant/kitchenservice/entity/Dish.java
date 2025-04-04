package ru.kaznacheev.restaurant.kitchenservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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

    /**
     * Сравнивает два блюда.
     *
     * @param o Объект для сравнения.
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
