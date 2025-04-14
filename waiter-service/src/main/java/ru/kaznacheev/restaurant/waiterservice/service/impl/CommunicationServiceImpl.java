package ru.kaznacheev.restaurant.waiterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kaznacheev.restaurant.common.dto.request.NewKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderPosition;
import ru.kaznacheev.restaurant.waiterservice.feign.KitchenFeignClient;
import ru.kaznacheev.restaurant.waiterservice.service.CommunicationService;
import ru.kaznacheev.restaurant.waiterservice.service.OrderPositionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link CommunicationService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    private final KitchenFeignClient kitchenFeignClient;
    private final OrderPositionService orderPositionService;

    /**
     * {@inheritDoc}
     *
     * @param orderId {@inheritDoc}
     */
    @Override
    public void sendOrderToKitchen(Long orderId) {
        List<OrderPosition> orderPositions = orderPositionService.getAllOrderPositionsByOrderId(orderId);
        NewKitchenOrderRequest request = NewKitchenOrderRequest.builder()
                .waiterOrderId(orderId)
                .dishes(orderPositions.stream()
                        .collect(Collectors.toMap(OrderPosition::getDishId, OrderPosition::getAmount)))
                .build();
        KitchenOrderFullInfoResponse response = kitchenFeignClient.sendOrderToKitchen(request);
        log.info("Заказ успешно отправлен, id на кухне: {}", response.getId());
    }

}
