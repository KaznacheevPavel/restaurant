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
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.dto.response.ResponseBodyWithData;
import ru.kaznacheev.restaurant.common.dto.response.ResponseDetailMessages;
import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.waiterservice.dto.NewOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.entity.Order;
import ru.kaznacheev.restaurant.waiterservice.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.util.List;
import java.util.Map;

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
     * @return {@link BaseResponseBody} с информацией о создании заказа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponseBody createOrder(@RequestBody NewOrderRequest newOrderRequest) {
        orderService.createOrder(newOrderRequest);
        return BaseResponseBody.builder()
                .title(ResponseTitle.CREATED.name())
                .status(ResponseTitle.CREATED.getStatus())
                .detail(ResponseDetailMessages.ORDER_CREATED.getDetail())
                .build();
    }

    /**
     * Возвращает заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponseBody} с информацией о заказе
     */
    @GetMapping("/{id}")
    public ResponseBodyWithData getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail(ResponseDetailMessages.GET_ORDER.getDetail())
                .data(Map.of("order", order))
                .build();
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link ResponseBodyWithData} со списком заказов
     */
    @GetMapping
    public ResponseBodyWithData getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail(ResponseDetailMessages.GET_ORDER_LIST.getDetail())
                .data(Map.of("orders", orders))
                .build();
    }

    /**
     * Возвращает статус заказа по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link ResponseBodyWithData} со статусом заказа
     */
    @GetMapping("/{id}/status")
    public ResponseBodyWithData getOrderStatusById(@PathVariable Long id) {
        OrderStatus orderStatus = orderService.getOrderStatusById(id);
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail(ResponseDetailMessages.GET_STATUS.getDetail())
                .data(Map.of("orderStatus", orderStatus))
                .build();
    }

}
