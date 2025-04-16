package ru.kaznacheev.restaurant.waiterservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.waiterservice.feign.KitchenFeignClient;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KitchenCommunicationServiceImplTest {

    @Mock
    private KitchenFeignClient kitchenFeignClient;

    @InjectMocks
    private KitchenCommunicationServiceImpl kitchenCommunicationService;

    @Test
    @DisplayName("Should send dish to kitchen service")
    void shouldSendDishToKitchen() {
        // Подготовка
        CreateKitchenDishRequest request = CreateKitchenDishRequest.builder()
                .id(12L)
                .shortName("Борщ")
                .composition("Свекла, капуста, картофель, морковь, лук, мясо, зелень")
                .build();

        // Действие
        kitchenCommunicationService.sendDishToKitchen(request);

        // Проверка
        verify(kitchenFeignClient).sendNewDishToKitchen(request);
    }

    @Test
    @DisplayName("Should send order to kitchen service")
    void shouldSendOrderToKitchen() {
        // Подготовка
        CreateKitchenOrderRequest request = CreateKitchenOrderRequest.builder()
                .waiterOrderId(32L)
                .dishes(Map.of(12L, 2L, 5L, 1L))
                .build();
        KitchenOrderResponse expectedResponse = KitchenOrderResponse.builder()
                .id(32L)
                .build();
        when(kitchenFeignClient.sendNewOrderToKitchen(request)).thenReturn(expectedResponse);

        // Действие
        kitchenCommunicationService.sendOrderToKitchen(request);

        // Проверка
        verify(kitchenFeignClient).sendNewOrderToKitchen(request);
    }

}