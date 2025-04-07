package ru.kaznacheev.restaurant.kitchenservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;

import java.util.List;

/**
 * Маппер для преобразования заказов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    /**
     * Преобразует {@link Order} в {@link OrderStatusResponse}.
     *
     * @param order Заказ
     * @return {@link OrderStatusResponse} со статусом заказа
     */
    OrderStatusResponse toOrderStatusResponse(Order order);

    /**
     * Преобразует {@link Order} в {@link OrderShortInfoResponse}.
     *
     * @param order Заказ
     * @return {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    OrderShortInfoResponse toOrderShortInfoResponse(Order order);

    /**
     * Преобразует {@link List} {@link Order} в {@link List} {@link OrderShortInfoResponse}.
     *
     * @param orders Заказы
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> toListOrderShortInfoResponse(List<Order> orders);

    /**
     * Преобразует {@link Order} в {@link KitchenOrderFullInfoResponse}.
     *
     * @param order Заказ
     * @return {@link KitchenOrderFullInfoResponse} с информацией о заказе
     */
    @Mapping(target = "dishes", expression = "java(toOrderPositionResponseList(order))")
    KitchenOrderFullInfoResponse toOrderFullInfoResponse(Order order);

    /**
     * Преобразует список заказанных блюд из {@link Order} в {@link List} {@link OrderPositionResponse}.
     *
     * @param order Заказ
     * @return {@link List} {@link OrderPositionResponse} с информацией о заказанных блюдах
     */
    default List<OrderPositionResponse> toOrderPositionResponseList(Order order) {
        return order.getOrderPositions().stream()
                .map(orderPosition ->
                        new OrderPositionResponse(orderPosition.getDish().getShortName(), orderPosition.getAmount()))
                .toList();
    }

}
