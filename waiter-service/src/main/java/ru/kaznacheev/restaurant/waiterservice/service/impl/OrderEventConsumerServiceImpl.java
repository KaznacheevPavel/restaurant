package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.waiterservice.service.EventHandlerService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderEventConsumerService;

/**
 * Реализация интерфейса {@link OrderEventConsumerService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumerServiceImpl implements OrderEventConsumerService {

    private final EventHandlerService<OrderEvent> eventHandlerService;

    @KafkaListener(topics = "${spring.kafka.producer.order-event-topic}",
            concurrency = "${spring.kafka.consumer.concurrency}")
    @Override
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Получено событие {} заказа {}", orderEvent.getEventType().name(), orderEvent.getOrderId());
        eventHandlerService.processEvent(orderEvent);
    }

}
