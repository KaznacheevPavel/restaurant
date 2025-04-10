package ru.kaznacheev.restaurant.kitchenservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Интерфейс клиента для взаимодействия с сервисом официантов.
 */
@FeignClient(value = "waiterFeignClient", url = "${waiter-service.url}")
public interface WaiterFeignClient {

    /**
     * Завершает заказ для официантов.
     *
     * @param waiterOrderId Идентификатор заказа официанта
     */
    @RequestMapping(method = RequestMethod.POST, path = "/api/v1/orders/{orderId}/complete")
    void completeOrderOnWaiter(@PathVariable("orderId") Long waiterOrderId);

}
