package ru.kaznacheev.restaurant.waiterservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kaznacheev.restaurant.common.dto.request.NewKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;

/**
 * Интерфейс клиента для взаимодействия с сервисом кухни.
 */
@FeignClient(value = "kitchenFeignClient", url = "${kitchen-service.url}")
public interface KitchenFeignClient {

    /**
     * Отправляет заказ в кухню.
     *
     * @param request DTO, содержащий информацию о новом заказе
     * @return {@link KitchenOrderFullInfoResponse} с информацией о заказе на кухне
     */
    @RequestMapping(method = RequestMethod.POST, path = "/api/v1/orders")
    KitchenOrderFullInfoResponse sendOrderToKitchen(NewKitchenOrderRequest request);

}
