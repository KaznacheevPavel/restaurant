package ru.kaznacheev.restaurant.kitchenservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Интерфейс клиента для взаимодействия с сервисом официантов.
 */
@FeignClient(value = "waiterFeignClient", url = "http://localhost:8082/api/v1/orders")
public interface WaiterFeignClient {

    /**
     * Завершает заказ для официантов.
     *
     * @param waiterOrderId Идентификатор заказа официанта
     */
    @RequestMapping(method = RequestMethod.POST, path = "/{orderId}/complete")
    void completeOrderOnWaiter(@PathVariable("orderId") Long waiterOrderId);

}
