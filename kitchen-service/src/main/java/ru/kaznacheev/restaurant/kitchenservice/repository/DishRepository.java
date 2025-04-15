package ru.kaznacheev.restaurant.kitchenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

/**
 * Репозиторий для работы с блюдами.
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    /**
     * Проверяет, существует ли блюдо с заданным сокращенным названием.
     *
     * @param shortName Сокращенное название блюда.
     * @return {@code true}, если блюдо с заданным сокращенным названием существует, {@code false} в противном случае.
     */
    boolean existsByShortName(String shortName);

}
