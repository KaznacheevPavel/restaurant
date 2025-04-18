package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDataTitle;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.kitchenservice.entity.Dish;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPositionCompositeId;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderPositionMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderPositionRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link OrderPositionService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;
    private final DishService dishService;
    private final OrderPositionMapper orderPositionMapper;

    /**
     * {@inheritDoc}
     *
     * @param order {@inheritDoc}
     * @param composition {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public List<OrderPositionResponse> addDishesToOrder(Order order, Map<Long, Long> composition) {
        log.info("Добавление позиций: {} к заказу с id: {}", composition, order.getId());
        List<Dish> foundedDishes = dishService.getAllDishesByIds(composition.keySet());
        validateAllDishesFound(foundedDishes, composition.keySet().stream().toList());
        validateAllDishesSufficient(foundedDishes, composition);
        List<OrderPosition> orderPositions = foundedDishes.stream()
                .map(dish -> {
                    Long orderedAmount = composition.get(dish.getId());
                    dish.setBalance(dish.getBalance() - orderedAmount);
                    return OrderPosition.builder()
                            .id(new OrderPositionCompositeId(order.getId(), dish.getId()))
                            .order(order)
                            .dish(dish)
                            .amount(orderedAmount)
                            .build();
                })
                .toList();
        orderPositionRepository.saveAll(orderPositions);
        order.setOrderPositions(orderPositions);
        log.info("Позиции: {} успешно добавлены к заказу с id: {}", composition, order.getId());
        return orderPositionMapper.toOrderPositionResponseList(orderPositions);
    }

    /**
     * {@inheritDoc}
     *
     * @param order {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderPositionResponse> getOrderPositions(Order order) {
        log.info("Получение информации о позициях заказа с id: {}", order.getId());
        List<OrderPosition> orderPositions = orderPositionRepository.findAllWithDishesByOrderId(order.getId());
        return orderPositionMapper.toOrderPositionResponseList(orderPositions);
    }

    /**
     * Проверяет, что все блюда из заказа существуют.
     *
     * @param foundedDishes Найденные блюда
     * @param orderedDishIds Идентификаторы блюд из заказа
     * @throws ConflictBaseException Если не все блюда из заказа существуют
     */
    private void validateAllDishesFound(List<Dish> foundedDishes, List<Long> orderedDishIds) {
        if (foundedDishes.size() != orderedDishIds.size()) {
            Set<Long> foundedIds = foundedDishes.stream()
                    .map(Dish::getId)
                    .collect(Collectors.toSet());
            List<Long> notFoundedIds = orderedDishIds.stream()
                    .filter(id -> !foundedIds.contains(id))
                    .toList();
            throw new ConflictBaseException(ExceptionDetail.ORDER_COMPOSITION_EXCEPTION.getTemplate(),
                    Map.of(ExceptionDataTitle.NOT_FOUNDED_DISHES.getTitle(), notFoundedIds));
        }
    }

    /**
     * Проверяет, что все блюда из заказа имеют достаточное количество порций.
     *
     * @param dishes Блюда
     * @param orderComposition Идентификаторы заказанных блюд и количество порций
     * @throws ConflictBaseException Если не все блюда имеют достаточное количество порций
     */
    private void validateAllDishesSufficient(List<Dish> dishes, Map<Long, Long> orderComposition) {
        List<Long> insufficientDishes = dishes.stream()
                .filter(dish -> dish.getBalance() < orderComposition.get(dish.getId()))
                .map(Dish::getId)
                .toList();
        if (!insufficientDishes.isEmpty()) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_COMPOSITION_EXCEPTION.getTemplate(),
                    Map.of(ExceptionDataTitle.INSUFFICIENT_DISHES.getTitle(), insufficientDishes));
        }
    }

}
