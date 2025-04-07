package ru.kaznacheev.restaurant.waiterservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.waiterservice.dto.request.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderFullInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

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
     * @param newOrderRequest DTO, содержащий информацию о новом заказе
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderFullInfoResponse createOrder(@RequestBody NewOrderRequest newOrderRequest) {
        return orderService.createOrder(newOrderRequest);
    }

    /**
     * Возвращает заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderFullInfoResponse} с информацией о заказе
     */
    @GetMapping("/{id}")
    public OrderFullInfoResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    @GetMapping
    public List<OrderShortInfoResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Возвращает статус заказа по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} со статусом заказа
     */
    @GetMapping("/{id}/status")
    public OrderStatusResponse getOrderStatusById(@PathVariable Long id) {
        return orderService.getOrderStatusById(id);
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

}
