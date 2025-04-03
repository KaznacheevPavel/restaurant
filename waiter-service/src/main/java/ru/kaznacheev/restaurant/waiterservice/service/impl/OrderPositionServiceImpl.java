package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.entity.Dish;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderPositionMapper;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;

import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса {@link OrderPositionService}.
 */
@Service
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionMapper orderPositionMapper;
    private final DishService dishService;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     * @param orderComposition {@inheritDoc}
     * @throws DishNotFoundException Если были переданы блюда с неверными названиями
     */
    @Transactional
    @Override
    public void addDishesToOrder(Long orderId, Map<String, Long> orderComposition) {
        List<Dish> dishes = dishService.getAllDishesByTitles(orderComposition.keySet().stream().toList());

        if (dishes.size() != orderComposition.size()) {
            List<String> notFoundedIds = getNotFoundedIds(dishes, orderComposition.keySet().stream().toList());
            throw new DishNotFoundException(notFoundedIds);
        }

        List<OrderPosition> orderPositions = dishes.stream()
                .map(dish -> OrderPosition.builder()
                        .orderId(orderId)
                        .dishId(dish.getId())
                        .amount(orderComposition.get(dish.getName()))
                        .build())
                .toList();
        orderPositionMapper.saveAll(orderPositions);
    }

    /**
     * Возвращает список несуществующих названий блюд.
     *
     * @param foundedDishes Список найденных блюд
     * @param titles Список всех переданных названий блюд
     * @return {@link List} несуществующих названий блюд
     */
    private List<String> getNotFoundedIds(List<Dish> foundedDishes, List<String> titles) {
        List<String> foundedIds = foundedDishes.stream()
                .map(Dish::getName)
                .toList();
        return titles.stream()
                .filter(title -> !foundedIds.contains(title))
                .toList();
    }

}
