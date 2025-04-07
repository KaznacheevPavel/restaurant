package ru.kaznacheev.restaurant.kitchenservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.NewOrderRequest;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderFullInfoResponse;
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
     * @param newOrderRequest DTO, содержащий информацию о заказе
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    OrderFullInfoResponse createOrder(@Valid NewOrderRequest newOrderRequest);

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
