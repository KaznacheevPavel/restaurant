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

import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса {@link OrderPositionService}.
 */
@Service
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;
    private final DishService dishService;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     * @param orderComposition {@inheritDoc}
     * @throws DishNotFoundException Если были переданы блюда с неверными идентификаторами
     * @throws InsufficientDishException Если было заказано больше блюд, чем есть в наличии
     */
    @Transactional
    @Override
    public void addDishesToOrder(Long orderId, Map<Long, Long> orderComposition) {
        List<Dish> dishes = dishService.getAllDishesByIds(orderComposition.keySet());

        if (dishes.size() != orderComposition.size()) {
            List<Long> notFoundedIds = getNotFoundedIds(dishes, orderComposition.keySet().stream().toList());
            throw new DishNotFoundException(notFoundedIds);
        }

        List<Long> insufficientDishes = getInsufficientDishes(dishes, orderComposition);
        if (!insufficientDishes.isEmpty()) {
            throw new InsufficientDishException(insufficientDishes);
        }

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

    /**
     * Возвращает список несуществующих идентификаторов блюд.
     *
     * @param foundedDishes Список найденных блюд
     * @param ids Список всех переданных идентификаторов блюд
     * @return {@link List} несуществующих идентификаторов блюд
     */
    private List<Long> getNotFoundedIds(List<Dish> foundedDishes, List<Long> ids) {
        List<Long> foundedIds = foundedDishes.stream()
                .map(Dish::getId)
                .toList();
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
