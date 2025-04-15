package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.waiterservice.feign.KitchenFeignClient;
import ru.kaznacheev.restaurant.waiterservice.service.KitchenCommunicationService;

/**
 * Реализация интерфейса {@link KitchenCommunicationService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenCommunicationServiceImpl implements KitchenCommunicationService {

    private final KitchenFeignClient kitchenFeignClient;

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     */
    @Override
    public void sendDishToKitchen(CreateKitchenDishRequest request) {
        log.info("Блюдо {} отправляется на кухню", request.getId());
        kitchenFeignClient.sendNewDishToKitchen(request);
        log.info("Блюдо {} успешно отправлено на кухню", request.getId());
    }

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     */
    @Override
    public void sendOrderToKitchen(CreateKitchenOrderRequest request) {
        log.info("Заказ {} отправляется на кухню", request.getWaiterOrderId());
        KitchenOrderResponse response = kitchenFeignClient.sendNewOrderToKitchen(request);
        log.info("Заказ {} успешно отправлен, id на кухне: {}", request.getWaiterOrderId(), response.getId());
    }

}
