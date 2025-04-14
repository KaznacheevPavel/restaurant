package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ.
     *
     * @param request DTO с информацией о новом заказе
     * @return {@link OrderResponse} с информацией о созданном заказе
     */
    OrderResponse createOrder(@Valid CreateOrderRequest request);

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderResponse} с информацией о заказе
     */
    OrderResponse getOrderById(Long id);

    /**
     * Возвращает список с краткой информацией о всех заказах.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    List<OrderShortInfoResponse> getAllOrders();

    /**
     * Возвращает информацию о статусе заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse getOrderStatus(Long id);

    /**
     * Подтверждает оплату заказа.
     *
     * @param id Идентификатор заказа
     */
    void paidForOrder(Long id);

    /**
     * Подтверждает приготовление заказа.
     *
     * @param id Идентификатор заказа
     */
    void cookOrder(Long id);

    /**
     * Отклоняет заказ.
     *
     * @param id Идентификатор заказа
     */
    void rejectOrder(Long id);

}
