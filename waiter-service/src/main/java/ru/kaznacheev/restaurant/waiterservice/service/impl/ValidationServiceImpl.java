package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;
import ru.kaznacheev.restaurant.waiterservice.service.ValidationService;

import java.util.List;
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
     * @param dishTitles {@inheritDoc}
     * @throws DishNotFoundException Если были переданы блюда с неверными названиями
     */
    @Override
    public void validateDishAddition(List<Dish> dishes, List<String> dishTitles) {
        if (dishes.size() != dishTitles.size()) {
            List<String> notFoundedIds = getNotFoundedIds(dishes, dishTitles);
            throw new DishNotFoundException(notFoundedIds);
        }
    }

    /**
     * Возвращает список несуществующих названий блюд.
     *
     * @param foundedDishes Список найденных блюд
     * @param titles Список всех переданных названий блюд
     * @return {@link List} несуществующих названий блюд
     */
    private List<String> getNotFoundedIds(List<Dish> foundedDishes, List<String> titles) {
        Set<String> foundedDishTitles = foundedDishes.stream()
                .map(Dish::getName)
                .collect(Collectors.toSet());
        return titles.stream()
                .filter(title -> !foundedDishTitles.contains(title))
                .toList();
    }

}
