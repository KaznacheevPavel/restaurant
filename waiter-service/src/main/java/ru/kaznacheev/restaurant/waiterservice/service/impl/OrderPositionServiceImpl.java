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
import ru.kaznacheev.restaurant.waiterservice.service.ValidationService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link OrderPositionService}.
 */
@Service
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionMapper orderPositionMapper;
    private final DishService dishService;
    private final ValidationService validationService;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     * @param orderComposition {@inheritDoc}
     */
    @Transactional
    @Override
    public void addDishesToOrder(Long orderId, Map<String, Long> orderComposition) {
        List<Dish> dishes = dishService.getAllDishesByTitles(orderComposition.keySet().stream().toList());
        validationService.validateDishAddition(dishes, orderComposition.keySet().stream().toList());
        List<OrderPosition> orderPositions = dishes.stream()
                .map(dish -> OrderPosition.builder()
                        .orderId(orderId)
                        .dishId(dish.getId())
                        .amount(orderComposition.get(dish.getName()))
                        .build())
                .toList();
        orderPositionMapper.saveAll(orderPositions);
    }

}
