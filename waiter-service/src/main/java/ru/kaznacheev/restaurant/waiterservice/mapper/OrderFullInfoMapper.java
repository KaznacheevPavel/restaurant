package ru.kaznacheev.restaurant.waiterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * Маппер для преобразования заказов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderFullInfoMapper {

    /**
     * Преобразует @{@link Order} и заказанные блюда в {@link OrderFullInfoResponse}.
     *
     * @param order Заказ
     * @param dishes Заказанные блюда и количество порций
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    @Mapping(target = "dishes", expression = "java(toOrderPositionResponseList(dishes))")
    OrderFullInfoResponse toOrderFullInfoResponse(Order order, Map<String, Long> dishes);

    /**
     * Преобразует заказанные блюда в {@link List} {@link OrderPositionResponse}.
     *
     * @param dishes Заказанные блюда и количество порций
     * @return {@link List} {@link OrderPositionResponse} с информацией о заказанных блюдах
     */
    default List<OrderPositionResponse> toOrderPositionResponseList(Map<String, Long> dishes) {
        return dishes.entrySet().stream()
                .map(entry -> new OrderPositionResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

}
