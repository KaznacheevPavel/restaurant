package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.kitchenservice.dto.NewOrderDto;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.kitchenservice.repository.SimpleRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

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
     * Принимает заказ на основе переданного DTO.
     *
     * @param newOrderDto DTO, содержащий информацию о заказе
     */
    @Override
    public void acceptOrder(NewOrderDto newOrderDto) {
        log.info("Accepting order {}", newOrderDto.hashCode());
        Order order = Order.builder()
                .dishes(newOrderDto.getDishes())
                .comment(newOrderDto.getComment())
                .status(OrderStatus.ACCEPTED)
                .build();
        orderRepository.save(order);
        log.info("Order with id: {} successfully accepted {}", order.getId(), newOrderDto.hashCode());
    }

    /**
     * Отклоняет заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    @Override
    public void rejectOrder(int id) {
        log.info("Rejecting order with id: {}", id);
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.REJECTED);
        log.info("Order with id: {} successfully rejected", id);
    }

    /**
     * Завершает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    @Override
    public void completeOrder(int id) {
        log.info("Completing order with id: {}", id);
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
        log.info("Order with id: {} successfully completed", id);
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
}
