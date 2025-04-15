package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования позиций заказа.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderPositionMapper {

    /**
     * Преобразует названия блюд, количество порций и {@link List} {@link DishResponse}
     * в {@link List} {@link OrderPositionResponse}.
     *
     * @param composition Названия заказанных блюд и количество порций
     * @param dishes Список блюд
     * @return {@link List} {@link OrderPositionResponse} с информацией о позиции заказа
     */
    default List<OrderPositionResponse> toOrderPositionResponseList(Map<String, Long> composition, List<DishResponse> dishes) {
        Map<String, Long> dishesIds = dishes.stream().collect(Collectors.toMap(DishResponse::getName, DishResponse::getId));
        return composition.entrySet().stream()
                .map(entry -> OrderPositionResponse.builder()
                        .dishId(dishesIds.get(entry.getKey()))
                        .name(entry.getKey())
                        .amount(entry.getValue())
                        .build())
                .toList();
    };

}
