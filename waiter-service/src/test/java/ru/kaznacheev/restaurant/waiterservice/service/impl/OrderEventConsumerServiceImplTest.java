package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEventType;
import ru.kaznacheev.restaurant.waiterservice.service.EventHandlerService;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderEventConsumerServiceImplTest {

    @Mock
    private EventHandlerService<OrderEvent> eventHandlerService;

    @InjectMocks
    private OrderEventConsumerServiceImpl orderEventConsumerService;

    @Test
    @DisplayName("Should consume order event")
    void shouldConsumeOrderEvent() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2025-01-01T12:00:00Z"), ZoneOffset.UTC);
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(12L)
                .eventType(OrderEventType.COMPLETED)
                .createdAt(OffsetDateTime.now(clock))
                .build();

        // Действие
        orderEventConsumerService.consumeOrderEvent(orderEvent);

        // Проверка
        verify(eventHandlerService).processEvent(orderEvent);
    }

}