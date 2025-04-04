package ru.kaznacheev.restaurant.kitchenservice.service;

import jakarta.validation.Valid;
import ru.kaznacheev.restaurant.kitchenservice.dto.NewOrderRequest;
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
     */
    void createOrder(@Valid NewOrderRequest newOrderRequest);

    /**
     * Отклоняет заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    void rejectOrder(Long id);

    /**
     * Завершает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    void completeOrder(Long id);

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
     * @return {@link List} {@link Order} Список всех заказов
     */
    List<Order> getAllOrders();

}
