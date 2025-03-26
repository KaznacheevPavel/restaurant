package ru.kaznacheev.restaurant.waiterservice.service;

import ru.kaznacheev.restaurant.waiterservice.dto.NewOrderDto;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;

import java.util.List;

/**
 * Интерфейс сервиса для обработки заказов.
 */
public interface OrderService {

    /**
     * Создает новый заказ на основе переданного DTO.
     *
     * @param newOrderDto DTO, содержащий информацию о новом заказе
     */
    void createOrder(NewOrderDto newOrderDto);

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

    /**
     * Возвращает статус заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Статус заказа
     */
    OrderStatus getOrderStatusById(int id);

}
