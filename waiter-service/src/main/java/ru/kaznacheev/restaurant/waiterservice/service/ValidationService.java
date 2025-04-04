package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.waiterservice.entity.Dish;

import java.util.List;

/**
 * Интерфейс сервиса для валидации.
 */
public interface ValidationService {

    /**
     * Проверяет, что все заказанные блюда существуют
     *
     * @param dishes Найденные блюда по названиям из заказа
     * @param dishTitles Названия блюд из заказа
     */
    void validateDishAddition(List<Dish> dishes, List<String> dishTitles);

}
