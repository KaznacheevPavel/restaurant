package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEventType;
import ru.kaznacheev.restaurant.waiterservice.service.OrderEventConsumerService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

/**
 * Реализация интерфейса {@link OrderEventConsumerService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumerServiceImpl implements OrderEventConsumerService {

    private final OrderService orderService;

    /**
     * {@inheritDoc}
     *
     * @param orderEvent {@inheritDoc}
     */
    @KafkaListener(topics = "${spring.kafka.producer.order-event-topic}", concurrency = "${spring.kafka.consumer.concurrency}")
    @Override
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Получено событие {} заказа {}", orderEvent.getEventType().name(), orderEvent.getOrderId());
        if (OrderEventType.COMPLETED.equals(orderEvent.getEventType())) {
            orderService.cookOrder(orderEvent.getOrderId());
        } else if (OrderEventType.REJECTED.equals(orderEvent.getEventType())) {
            orderService.rejectOrder(orderEvent.getOrderId());
        }
    }

}
