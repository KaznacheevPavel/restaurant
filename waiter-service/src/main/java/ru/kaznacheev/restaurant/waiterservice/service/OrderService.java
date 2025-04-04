package ru.kaznacheev.restaurant.waiterservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.waiterservice.dto.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.util.List;

/**
 * Интерфейс сервиса для работы с заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ на основе переданного DTO.
     *
     * @param newOrderRequest DTO, содержащий информацию о новом заказе
     */
    void createOrder(@Valid NewOrderRequest newOrderRequest);

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link Order} Заказ
     */
    Order getOrderById(Long id);

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link Order} Список всех заказов
     */
    List<Order> getAllOrders();

    /**
     * Возвращает статус заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatus} Статус заказа
     */
    OrderStatus getOrderStatusById(Long id);

}
