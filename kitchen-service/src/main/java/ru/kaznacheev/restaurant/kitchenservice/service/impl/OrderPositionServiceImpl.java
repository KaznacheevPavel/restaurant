package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.common.exception.DishNotFoundException;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;
import ru.kaznacheev.restaurant.kitchenservice.exception.InsufficientDishException;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderPositionRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.kitchenservice.service.ValidationService;

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

    private final OrderPositionRepository orderPositionRepository;
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
    public void addDishesToOrder(Long orderId, Map<Long, Long> orderComposition) {
        List<Dish> dishes = dishService.getAllDishesByIds(orderComposition.keySet());
        validationService.validateDishAddition(dishes, orderComposition);
        List<OrderPosition> orderPositions = dishes.stream()
                .map(dish -> {
                    Long orderedAmount = orderComposition.get(dish.getId());
                    dish.setBalance(dish.getBalance() - orderedAmount);
                    return OrderPosition.builder()
                            .id(new OrderPositionCompositeId(orderId, dish.getId()))
                            .amount(orderedAmount)
                            .build();
                })
                .toList();
        orderPositionRepository.saveAll(orderPositions);
    }

}
