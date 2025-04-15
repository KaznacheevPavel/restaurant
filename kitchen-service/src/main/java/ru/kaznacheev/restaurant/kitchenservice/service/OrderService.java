package ru.kaznacheev.restaurant.kitchenservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ.
     *
     * @param request DTO с информацией о новом заказе
     * @return {@link KitchenOrderResponse} с информацией о созданном заказе
     */
    KitchenOrderResponse createOrder(@Valid CreateKitchenOrderRequest request);

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link KitchenOrderResponse} с информацией о заказе
     */
    KitchenOrderResponse getOrderById(Long id);

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
     * Завершает приготовление заказа.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse completeOrder(Long id);

    /**
     * Отклоняет заказ.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    OrderStatusResponse rejectOrder(Long id);

}
