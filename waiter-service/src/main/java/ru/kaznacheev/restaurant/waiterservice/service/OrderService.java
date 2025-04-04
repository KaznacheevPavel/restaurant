package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.request.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderStatusResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ на основе переданного DTO.
     *
     * @param newOrderRequest DTO, содержащий информацию о новом заказе
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    OrderFullInfoResponse createOrder(@Valid NewOrderRequest newOrderRequest);

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    OrderFullInfoResponse getOrderById(Long id);

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> getAllOrders();

    /**
     * Возвращает статус заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} со статусом заказа
     */
    OrderStatusResponse getOrderStatusById(Long id);

}
