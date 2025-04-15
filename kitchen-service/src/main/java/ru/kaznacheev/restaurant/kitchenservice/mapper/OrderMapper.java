package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;

import java.util.List;

/**
 * Маппер для преобразования заказов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    /**
     * Преобразует {@link Order} и {@link List} {@link OrderPositionResponse} в {@link KitchenOrderResponse}.
     *
     * @param order Сущность заказа
     * @param dishes Список позиций заказа
     * @return {@link KitchenOrderResponse} с информацией о заказе
     */
    KitchenOrderResponse toKitchenOrderResponse(Order order, List<OrderPositionResponse> dishes);

    /**
     * Преобразует {@link List} {@link Order} в {@link List} {@link OrderShortInfoResponse}.
     *
     * @param order Список сущностей заказов
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> toOrderShortInfoResponseList(List<Order> order);

    /**
     * Преобразует {@link Order} в {@link OrderStatusResponse}.
     *
     * @param order Сущность заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse toOrderStatusResponse(Order order);

}
