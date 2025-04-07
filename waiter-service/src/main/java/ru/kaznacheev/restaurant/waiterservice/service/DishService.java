package ru.kaznacheev.restaurant.waiterservice.service;

import org.apache.ibatis.annotations.Param;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.util.List;

/**
 * Интерфейс сервиса для работы с блюдами.
 */
public interface DishService {

    /**
     * Возвращает список блюд по названиям.
     *
     * @param dishTitles Названия блюд
     * @return {@link List} {@link Dish} Список блюд
     */
    List<Dish> getAllDishesByTitles(@Param("dishTitles") List<String> dishTitles);

}
