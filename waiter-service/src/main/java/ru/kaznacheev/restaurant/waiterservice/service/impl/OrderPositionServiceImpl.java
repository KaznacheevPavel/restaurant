package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDataTitle;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.waiterservice.mapper.OrderPositionMapper;
import ru.kaznacheev.restaurant.waiterservice.repository.OrderPositionRepository;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;

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
    private final OrderPositionMapper orderPositionMapper;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     * @param composition {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<OrderPositionResponse> createOrderComposition(Long orderId, Map<String, Long> composition) {
        List<String> orderedDishTitles = composition.keySet().stream().toList();
        List<DishResponse> foundedDishes = dishService.getAllDishesByNames(orderedDishTitles);
        validateFoundedDishes(foundedDishes, orderedDishTitles);
        List<OrderPosition> orderPositions = foundedDishes.stream()
                .map(dish -> OrderPosition.builder()
                        .orderId(orderId)
                        .dishId(dish.getId())
                        .amount(composition.get(dish.getName()))
                        .build())
                .toList();
        orderPositionRepository.saveAll(orderPositions);
        return orderPositionMapper.toOrderPositionResponseList(composition, foundedDishes);
    }

    /**
     * Проверяет, что все блюда из заказа существуют.
     *
     * @param foundedDishes Найденных блюд
     * @param orderDishTitles Названия блюд из заказа
     * @throws ConflictBaseException Если не все блюда из заказа существуют
     */
    private void validateFoundedDishes(List<DishResponse> foundedDishes, List<String> orderDishTitles) {
        Set<String> foundedTitles = foundedDishes.stream()
                .map(DishResponse::getName)
                .collect(Collectors.toSet());
        if (foundedTitles.size() != orderDishTitles.size()) {
            List<String> notFoundedDishTitles = orderDishTitles.stream()
                    .filter(title -> !foundedTitles.contains(title))
                    .toList();
            throw new ConflictBaseException(ExceptionDetail.ORDER_COMPOSITION_EXCEPTION.getTemplate(),
                    Map.of(ExceptionDataTitle.NOT_FOUNDED_DISHES.getTitle(), notFoundedDishTitles));
        }
    }

}
