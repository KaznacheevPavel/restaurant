package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEventType;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderEventProducerService;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Реализация интерфейса {@link OrderEventProducerService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventProducerServiceImpl implements OrderEventProducerService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final Clock clock;

    @Value("${spring.kafka.producer.order-event-topic}")
    private String topicName;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     */
    @Override
    public void sendOrderCompletedEvent(Long orderId) {
        log.info("Создание события о завершении заказа с id: {}", orderId);
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(orderId)
                .eventType(OrderEventType.COMPLETED)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        sendEvent(orderEvent);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     */
    @Override
    public void sendOrderRejectedEvent(Long orderId) {
        log.info("Создание события об отмене заказа с id {}", orderId);
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(orderId)
                .eventType(OrderEventType.REJECTED)
                .createdAt(OffsetDateTime.now(clock))
                .build();
        sendEvent(orderEvent);
    }

    /**
     * Отправляет событие заказа в Kafka.
     *
     * @param orderEvent DTO с информацией о событии заказа
     */
    private void sendEvent(OrderEvent orderEvent) {
        log.info("Событие заказа {} отправляется в топик {}", orderEvent.getOrderId(), topicName);
        CompletableFuture<SendResult<String, OrderEvent>> future =
                kafkaTemplate.send(topicName, String.valueOf(orderEvent.getOrderId()), orderEvent);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Событие заказа {} успешно отправлено в топик {}", orderEvent.getOrderId(), topicName);
            } else {
                log.error("Ошибка отправки события заказа {} в топик {}: {}",
                        orderEvent.getOrderId(), topicName, exception.getStackTrace());
            }
        });
    }
}
