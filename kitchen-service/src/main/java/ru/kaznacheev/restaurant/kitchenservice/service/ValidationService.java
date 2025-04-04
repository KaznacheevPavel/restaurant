package ru.kaznacheev.restaurant.kitchenservice.service;

import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса для валидации.
 */
public interface ValidationService {

    /**
     * Проверяет, что все заказанные блюда существуют и доступны в нужном количестве.
     *
     * @param dishes Найденные блюда по идентификаторам из заказа
     * @param orderComposition Состав заказа
     */
    void validateDishAddition(List<Dish> dishes, Map<Long, Long> orderComposition);

}
