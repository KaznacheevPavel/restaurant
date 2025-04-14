package ru.kaznacheev.restaurant.kitchenservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.kitchenservice.feign.WaiterFeignClient;
import ru.kaznacheev.restaurant.kitchenservice.service.CommunicationService;

/**
 * Реализация интерфейса {@link CommunicationService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    private final WaiterFeignClient waiterFeignClient;

    /**
     * {@inheritDoc}
     *
     * @param waiterOrderId {@inheritDoc}
     */
    @Override
    public void completeOrderOnWaiter(Long waiterOrderId) {
        waiterFeignClient.completeOrderOnWaiter(waiterOrderId);
        log.info("Заказ {} успешно завершен и передан официантам", waiterOrderId);
    }

}
