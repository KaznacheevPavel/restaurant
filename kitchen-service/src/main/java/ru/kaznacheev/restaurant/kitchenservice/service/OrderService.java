package ru.kaznacheev.restaurant.kitchenservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.common.dto.request.NewOrderToKitchenRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;

import java.util.List;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ на основе переданного DTO.
     *
     * @param newOrderToKitchenRequest DTO, содержащий информацию о заказе
     * @return {@link KitchenOrderFullInfoResponse} с информацией о заказе
     */
    KitchenOrderFullInfoResponse createOrder(@Valid NewOrderToKitchenRequest newOrderToKitchenRequest);

    /**
     * Отклоняет заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse rejectOrder(Long id);

    /**
     * Завершает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse completeOrder(Long id);

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Заказ
     */
    Order getOrderById(Long id);

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> getAllOrders();

}
