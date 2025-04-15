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
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
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
     * Создает новый заказ.
     *
     * @param request DTO, с информацией о новом заказе
     * @return {@link KitchenOrderResponse} с информацией о созданном заказе
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOrderResponse createOrder(@RequestBody CreateKitchenOrderRequest request) {
        return orderService.createOrder(request);
    }

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link KitchenOrderResponse} с информацией о заказе
     */
    @GetMapping("/{id}")
    public KitchenOrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Возвращает список с краткой информацией о всех заказах.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    @GetMapping
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Возвращает информацию о статусе заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @GetMapping("/{id}/status")
    public OrderStatusResponse getOrderStatus(@PathVariable Long id) {
        return orderService.getOrderStatus(id);
    }

    /**
     * Завершает приготовление заказа.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @PostMapping("/{id}/complete")
    public OrderStatusResponse completeOrder(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }

    /**
     * Отменяет заказ.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @PostMapping("/{id}/reject")
    public OrderStatusResponse rejectOrder(@PathVariable Long id) {
        return orderService.rejectOrder(id);
    }

}
