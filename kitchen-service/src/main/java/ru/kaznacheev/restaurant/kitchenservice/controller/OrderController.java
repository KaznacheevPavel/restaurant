package ru.kaznacheev.restaurant.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.common.dto.request.NewKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderFullInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     *  Создает новый заказ.
     *
     * @param newKitchenOrderRequest DTO, содержащий информацию о новом заказе
     * @return {@link KitchenOrderFullInfoResponse} с информацией о заказе
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOrderFullInfoResponse createOrder(@RequestBody NewKitchenOrderRequest newKitchenOrderRequest) {
        return orderService.createOrder(newKitchenOrderRequest);
    }

    /**
     * Отклоняет заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @PostMapping("/{id}/reject")
    public OrderStatusResponse rejectOrder(@PathVariable Long id) {
        return orderService.rejectOrder(id);
    }

    /**
     * Завершает заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @PostMapping("/{id}/complete")
    public OrderStatusResponse completeOrder(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }

    /**
     *  Возвращает список всех заказов.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    @GetMapping
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

}
