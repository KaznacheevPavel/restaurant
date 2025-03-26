package ru.kaznacheev.restaurant.waiterservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.restaurant.common.dto.NewOrderDto;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponse;
import ru.kaznacheev.restaurant.common.dto.response.ResponseWithData;
import ru.kaznacheev.restaurant.common.entity.Order;
import ru.kaznacheev.restaurant.common.entity.OrderStatus;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Создает новый заказ.
     *
     * @param newOrderDto DTO, содержащий информацию о новом заказе
     * @return {@link BaseResponse} с информацией о создании заказа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createOrder(@RequestBody NewOrderDto newOrderDto) {
        log.info("POST: /api/v1/orders {}", newOrderDto.hashCode());
        orderService.createOrder(newOrderDto);
        return BaseResponse.builder()
                .title("CREATED")
                .status(HttpStatus.CREATED.value())
                .detail("Заказ успешно создан")
                .build();
    }

    /**
     * Возвращает заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponse} с информацией о заказе
     */
    @GetMapping("/{id}")
    public ResponseWithData getOrderById(@PathVariable int id) {
        log.info("GET: /api/v1/orders/{}", id);
        Order order = orderService.getOrderById(id);
        return ResponseWithData.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Заказ успешно получен")
                .data(Map.of("order", order))
                .build();
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return {@link ResponseWithData} со списком заказов
     */
    @GetMapping
    public ResponseWithData getAllOrders() {
        log.info("GET: /api/v1/orders");
        List<Order> orders = orderService.getAllOrders();
        return ResponseWithData.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Список заказов успешно получен")
                .data(Map.of("orders", orders))
                .build();
    }

    /**
     * Возвращает статус заказа по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link ResponseWithData} со статусом заказа
     */
    @GetMapping("/{id}/status")
    public ResponseWithData getOrderStatusById(@PathVariable int id) {
        log.info("GET: /api/v1/orders/{}/status", id);
        OrderStatus orderStatus = orderService.getOrderStatusById(id);
        return ResponseWithData.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Статус заказа успешно получен")
                .data(Map.of("orderStatus", orderStatus))
                .build();
    }

}
