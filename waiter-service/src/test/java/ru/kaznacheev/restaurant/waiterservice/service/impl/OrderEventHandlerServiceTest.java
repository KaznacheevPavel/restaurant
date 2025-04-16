package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEventType;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class OrderEventHandlerServiceTest {

    @Mock
    private OrderService orderService;
    @Mock
    private final Map<OrderEventType, Consumer<OrderEvent>> handlers = new HashMap<>();

    @InjectMocks
    private OrderEventHandlerService orderEventHandlerService;

    @BeforeEach
    void init() {
        orderEventHandlerService.init();
    }

    @Test
    @DisplayName("Should process event")
    void shouldProcessEvent() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2025-01-01T12:00:00Z"), ZoneOffset.UTC);
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(12L)
                .eventType(OrderEventType.COMPLETED)
                .createdAt(OffsetDateTime.now(clock))
                .build();

        // Действие
        orderEventHandlerService.processEvent(orderEvent);

        // Проверка
        verify(orderService).cookOrder(12L);
    }

    @Test
    @DisplayName("Should not process null event type")
    void shouldNotProcessEvent() {
        // Подготовка
        Clock clock = Clock.fixed(Instant.parse("2025-01-01T12:00:00Z"), ZoneOffset.UTC);
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(12L)
                .eventType(null)
                .createdAt(OffsetDateTime.now(clock))
                .build();

        // Действие
        orderEventHandlerService.processEvent(orderEvent);

        // Проверка
        verifyNoInteractions(orderService);
    }

}