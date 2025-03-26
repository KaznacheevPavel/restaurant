package ru.kaznacheev.restaurant.kitchenservice.service;

import ru.kaznacheev.restaurant.common.dto.NewOrderDto;
import ru.kaznacheev.restaurant.common.entity.Order;

import java.util.List;

/**
 * Интерфейс сервиса для обработки заказов.
 */
public interface OrderService {

    /**
     * Принимает заказ на основе переданного DTO.
     *
     * @param newOrderDto DTO, содержащий информацию о заказе
     */
    void acceptOrder(NewOrderDto newOrderDto);

    /**
     * Отклоняет заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    void rejectOrder(int id);

    /**
     * Завершает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    void completeOrder(int id);

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Заказ
     */
    Order getOrderById(int id);

    /**
     * Возвращает список всех заказов.
     *
     * @return Список всех заказов
     */
    List<Order> getAllOrders();

}
