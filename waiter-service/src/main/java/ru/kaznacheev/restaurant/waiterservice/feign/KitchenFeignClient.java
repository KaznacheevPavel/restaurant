package ru.kaznacheev.restaurant.waiterservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kaznacheev.restaurant.common.dto.request.NewOrderToKitchenRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;

/**
 * Интерфейс клиента для взаимодействия с сервисом кухни.
 */
@FeignClient(value = "kitchenFeignClient", url = "http://localhost:8081/api/v1/orders")
public interface KitchenFeignClient {

    /**
     * Отправляет заказ в кухню.
     *
     * @param request DTO, содержащий информацию о новом заказе
     * @return {@link KitchenOrderFullInfoResponse} с информацией о заказе на кухне
     */
    @RequestMapping(method = RequestMethod.POST)
    KitchenOrderFullInfoResponse sendOrderToKitchen(NewOrderToKitchenRequest request);

}
