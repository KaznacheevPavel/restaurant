package ru.kaznacheev.restaurant.waiterservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEvent;
import ru.kaznacheev.restaurant.common.dto.kafka.OrderEventType;
import ru.kaznacheev.restaurant.waiterservice.service.EventHandlerService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Реализация интерфейса {@link EventHandlerService} для обработки событий заказа.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandlerService implements EventHandlerService<OrderEvent> {

    private final OrderService orderService;
    private final Map<OrderEventType, Consumer<OrderEvent>> handlers = new HashMap<>();

    /**
     * Инициализирует возможные события заказа и выполняемые действия для них.
     */
    @PostConstruct
    public void init() {
        handlers.put(OrderEventType.COMPLETED, event -> orderService.cookOrder(event.getOrderId()));
        handlers.put(OrderEventType.REJECTED, event -> orderService.rejectOrder(event.getOrderId()));
    }

    /**
     * {@inheritDoc}
     *
     * @param event {@inheritDoc}
     */
    @Override
    public void processEvent(OrderEvent event) {
        Consumer<OrderEvent> handler = handlers.get(event.getEventType());
        if (handler != null) {
            log.info("Обработка события {} заказа с id: {}", event.getEventType(), event.getOrderId());
            handler.accept(event);
        } else {
            log.error("Получен неизвестный тип события {} заказа с id: {}", event.getEventType(), event.getOrderId());
        }
    }

}
