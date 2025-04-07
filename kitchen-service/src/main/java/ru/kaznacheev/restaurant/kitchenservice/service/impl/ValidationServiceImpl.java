package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.exception.InsufficientDishException;
import ru.kaznacheev.restaurant.kitchenservice.service.ValidationService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link ValidationService}.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    /**
     * {@inheritDoc}
     *
     * @param dishes {@inheritDoc}
     * @param orderComposition {@inheritDoc}
     * @throws DishNotFoundException Если были переданы блюда с неверными названиями
     * @throws InsufficientDishException Если было заказано больше порций, чем есть в наличии
     */
    @Override
    public void validateDishAddition(List<Dish> dishes, Map<Long, Long> orderComposition) {
        if (dishes.size() != orderComposition.size()) {
            List<Long> notFoundedIds = getNotFoundedIds(dishes, orderComposition.keySet().stream().toList());
            throw new DishNotFoundException(notFoundedIds);
        }

        List<Long> insufficientDishes = getInsufficientDishes(dishes, orderComposition);
        if (!insufficientDishes.isEmpty()) {
            throw new InsufficientDishException(insufficientDishes);
        }
    }

    /**
     * Возвращает список несуществующих идентификаторов блюд.
     *
     * @param foundedDishes Список найденных блюд
     * @param ids Список всех переданных идентификаторов блюд
     * @return {@link List} несуществующих идентификаторов блюд
     */
    private List<Long> getNotFoundedIds(List<Dish> foundedDishes, List<Long> ids) {
        Set<Long> foundedIds = foundedDishes.stream()
                .map(Dish::getId)
                .collect(Collectors.toSet());
        return ids.stream()
                .filter(id -> !foundedIds.contains(id))
                .toList();
    }

    /**
     * Возвращает список идентификаторов блюд, количество которых недостаточно для заказа.
     *
     * @param dishes Список блюд
     * @param orderComposition Идентификаторы блюд и их количество
     * @return {@link List} идентификаторов блюд, количество которых недостаточно для заказа
     */
    private List<Long> getInsufficientDishes(List<Dish> dishes, Map<Long, Long> orderComposition) {
        return dishes.stream()
                .filter(dish -> dish.getBalance() < orderComposition.get(dish.getId()))
                .map(Dish::getId)
                .toList();
    }

}
