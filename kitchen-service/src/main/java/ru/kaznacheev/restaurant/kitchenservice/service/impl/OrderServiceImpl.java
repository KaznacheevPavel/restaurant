package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.exception.OrderNotFoundException;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.NewOrderRequest;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    /**
     * {@inheritDoc}
     *
     * @param newOrderRequest {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderFullInfoResponse createOrder(@Valid NewOrderRequest newOrderRequest) {
        Order order = Order.builder()
                .waiterOrderId(newOrderRequest.getWaiterOrderId())
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        orderRepository.save(order);
        List<OrderPosition> orderPositions = orderPositionService.addDishesToOrder(order, newOrderRequest.getDishes());
        order.setOrderPositions(orderPositions);
        order.setStatus(OrderStatus.IN_PROGRESS);
        return orderMapper.toOrderFullInfoResponse(order);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderStatusResponse rejectOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.REJECTED);
        return orderMapper.toOrderStatusResponse(order);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderStatusResponse completeOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
        return orderMapper.toOrderStatusResponse(order);
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
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderMapper.toListOrderShortInfoResponse(orderRepository.findAll());
    }
}
