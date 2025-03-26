package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.waiterservice.dto.NewOrderDto;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.waiterservice.repository.SimpleRepository;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса сервиса для обработки заказов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final SimpleRepository<Integer, Order> orderRepository;

    /**
     * Создает новый заказ на основе переданного DTO.
     *
     * @param newOrderDto DTO, содержащий информацию о новом заказе
     */
    @Override
    public void createOrder(NewOrderDto newOrderDto) {
        log.info("Creating order {}", newOrderDto.hashCode());
        Order order = Order.builder()
                .dishes(newOrderDto.getDishes())
                .comment(newOrderDto.getComment())
                .status(OrderStatus.NEW)
                .build();
        orderRepository.save(order);
        log.info("Order with id: {} successfully created {}", order.getId(), newOrderDto.hashCode());
    }

    /**
     * Возвращает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Заказ
     * @throws OrderNotFoundException если заказ не найден
     */
    @Override
    public Order getOrderById(int id) {
        log.info("Getting order with id: {}", id);
        Optional<Order> order = orderRepository.getOrderById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return Список всех заказов
     */
    @Override
    public List<Order> getAllOrders() {
        log.info("Getting all orders");
        return orderRepository.getAllOrders();
    }

    /**
     * Возвращает статус заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Статус заказа
     */
    @Override
    public OrderStatus getOrderStatusById(int id) {
        log.info("Getting order status with id: {}", id);
        Order order = getOrderById(id);
        return order.getStatus();
    }
}
