package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;

import java.util.List;

/**
 * Маппер для преобразования позиций заказа.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderPositionMapper {

    /**
     * Преобразует {@link List} {@link OrderPosition} в {@link List} {@link OrderPositionResponse}.
     *
     * @param orderPositions Список позиций заказа
     * @return {@link List} {@link OrderPositionResponse} с информацией о позиции заказа
     */
    default List<OrderPositionResponse> toOrderPositionResponseList(List<OrderPosition> orderPositions) {
        return orderPositions.stream()
                .map(position -> OrderPositionResponse.builder()
                        .dishId(position.getId().getDishId())
                        .name(position.getDish().getShortName())
                        .amount(position.getAmount())
                        .build())
                .toList();
    }

}
