package ru.kaznacheev.restaurant.waiterservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;

/**
 * Интерфейс клиента для взаимодействия с сервисом кухни.
 */
@FeignClient(value = "kitchenFeignClient", url = "${kitchen-service.url}")
public interface KitchenFeignClient {

    /**
     * Отправляет новое блюдо на кухню.
     *
     * @param request DTO с информацией о новом блюде
     * @return {@link KitchenDishResponse} с информацией о созданном блюде
     */
    @RequestMapping(value = "/api/v1/dishes", method = RequestMethod.POST)
    KitchenDishResponse sendNewDishToKitchen(CreateKitchenDishRequest request);

    /**
     * Отправляет новый заказ на кухню.
     *
     * @param request DTO с информацией о новом заказе
     * @return {@link KitchenOrderResponse} с информацией о созданном заказе
     */
    @RequestMapping(value = "/api/v1/orders", method = RequestMethod.POST)
    KitchenOrderResponse sendNewOrderToKitchen(CreateKitchenOrderRequest request);

}
