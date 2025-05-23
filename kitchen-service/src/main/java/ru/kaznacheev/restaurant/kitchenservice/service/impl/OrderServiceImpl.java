package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderPositionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.common.exception.ConflictBaseException;
import ru.kaznacheev.restaurant.common.exception.ExceptionDetail;
import ru.kaznacheev.restaurant.common.exception.NotFoundBaseException;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.kitchenservice.mapper.OrderMapper;
import ru.kaznacheev.restaurant.kitchenservice.repository.OrderRepository;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderEventProducerService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderPositionService;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Реализация интерфейса {@link OrderService}.
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final OrderPositionService orderPositionService;
    private final OrderEventProducerService orderEventProducerService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public KitchenOrderResponse createOrder(@Valid CreateKitchenOrderRequest request) {
        log.info("Создание заказа для официанта с id: {} и составом: {}",
                request.getWaiterOrderId(), request.getDishes());
        Order order = Order.builder()
                .waiterOrderId(request.getWaiterOrderId())
                .status(OrderStatus.NEW)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        orderRepository.save(order);
        List<OrderPositionResponse> dishes = orderPositionService.addDishesToOrder(order, request.getDishes());
        order.setStatus(OrderStatus.IN_PROGRESS);
        log.info("Заказ для официанта с id: {} успешно создан, id заказа: {} ,состав: {}",
                order.getWaiterOrderId(), order.getId(), request.getDishes());
        return orderMapper.toKitchenOrderResponse(order, dishes);
    }

    @Transactional(readOnly = true)
    @Override
    public KitchenOrderResponse getOrderById(Long id) {
        log.debug("Получение информации о заказе с id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        List<OrderPositionResponse> dishes = orderPositionService.getOrderPositions(order);
        return orderMapper.toKitchenOrderResponse(order, dishes);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderShortInfoResponse> getAllOrders() {
        log.debug("Получение информации о всех заказах");
        return orderMapper.toOrderShortInfoResponseList(orderRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderStatusResponse getOrderStatus(Long id) {
        log.debug("Получение информации о статусе заказа с id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        return orderMapper.toOrderStatusResponse(order);
    }

    @Transactional
    @Override
    public OrderStatusResponse completeOrder(Long id) {
        log.info("Завершение приготовления заказа с id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        if (!OrderStatus.IN_PROGRESS.equals(order.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_COOK_EXCEPTION.format(id, OrderStatus.IN_PROGRESS));
        }
        order.setStatus(OrderStatus.COMPLETED);
        orderEventProducerService.sendOrderCompletedEvent(order.getWaiterOrderId());
        log.info("Заказ с id: {} успешно завершен", id);
        return orderMapper.toOrderStatusResponse(order);
    }

    @Transactional
    @Override
    public OrderStatusResponse rejectOrder(Long id) {
        log.info("Отклонение заказа с id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundBaseException(ExceptionDetail.ORDER_NOT_FOUND_BY_ID.format(id)));
        if (!OrderStatus.IN_PROGRESS.equals(order.getStatus())) {
            throw new ConflictBaseException(ExceptionDetail.ORDER_REJECT_EXCEPTION.format(id, OrderStatus.IN_PROGRESS));
        }
        order.setStatus(OrderStatus.REJECTED);
        orderEventProducerService.sendOrderRejectedEvent(order.getWaiterOrderId());
        log.info("Заказ с id: {} успешно отклонен", id);
        return orderMapper.toOrderStatusResponse(order);
    }

}
