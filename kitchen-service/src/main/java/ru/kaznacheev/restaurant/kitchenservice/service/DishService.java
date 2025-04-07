package ru.kaznacheev.restaurant.kitchenservice.service;

import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

import java.util.List;

/**
 * Интерфейс сервиса для работы с блюдами.
 */
public interface DishService {

    /**
     * Возвращает список блюд по идентификаторам.
     *
     * @param ids Идентификаторы блюд
     * @return {@link List} {@link Dish} Список блюд
     */
    List<Dish> getAllDishesByIds(Iterable<Long> ids);

}
