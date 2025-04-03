package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.kitchenservice.dto.NewOrderDto;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link OrderService}.
 */
@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final OrderPositionService orderPositionService;

    /**
     * {@inheritDoc}
     *
     * @param newOrderDto {@inheritDoc}
     */
    @Transactional
    @Override
    public void createOrder(@Valid NewOrderDto newOrderDto) {
        Order order = Order.builder()
                .waiterOrderId(newOrderDto.getWaiterOrderId())
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        orderRepository.save(order);
        orderPositionService.addDishesToOrder(order.getId(), newOrderDto.getDishes());
        order.setStatus(OrderStatus.IN_PROGRESS);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     */
    @Transactional
    @Override
    public void rejectOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.REJECTED);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     */
    @Transactional
    @Override
    public void completeOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws OrderNotFoundException Если заказ не найден
     */
    @Transactional(readOnly = true)
    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
