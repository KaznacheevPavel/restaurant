package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * Маппер для преобразования заказов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    /**
     * Преобразует {@link Order}, {@link List} {@link OrderPositionResponse} и стоимость заказа в {@link OrderResponse}.
     *
     * @param order Сущность заказа
     * @param cost Стоимость заказа
     * @param composition Состав заказа
     * @return {@link OrderResponse} с информацией о заказе
     */
    OrderResponse toOrderResponse(Order order, BigDecimal cost, List<OrderPositionResponse> composition);


}
