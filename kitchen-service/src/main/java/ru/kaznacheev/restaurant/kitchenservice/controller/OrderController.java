package ru.kaznacheev.restaurant.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.restaurant.common.dto.NewOrderDto;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.common.dto.response.ResponseBodyWithData;
import ru.kaznacheev.restaurant.kitchenservice.entity.Order;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

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
     *  Принимает заказ на основе переданного DTO.
     *
     * @param newOrderDto DTO, содержащий информацию о заказе
     * @return {@link BaseResponseBody} с информацией о создании заказа
     */
    @PostMapping
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
    public BaseResponseBody acceptOrder(@RequestBody NewOrderDto newOrderDto) {
        log.info("POST: /api/v1/orders {}", newOrderDto.hashCode());
        orderService.acceptOrder(newOrderDto);
        return BaseResponseBody.builder()
                .title(ResponseTitle.CREATED.name())
                .status(ResponseTitle.CREATED.getStatus())
                .detail("Заказ успешно принят")
                .build();
    }

    /**
     * Отклоняет заказ по указанному идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponseBody} с информацией об отмене заказа
     */
    @PostMapping("/{id}/reject")
    public BaseResponseBody rejectOrder(@PathVariable int id) {
        log.info("GET: /api/v1/{}/reject", id);
        orderService.rejectOrder(id);
        return BaseResponseBody.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail("Заказ успешно отменен")
                .build();
    }

    /**
     * Завершает заказ по указанному идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponseBody} с информацией о завершении заказа
     */
    @PostMapping("/{id}/complete")
    public BaseResponseBody completeOrder(@PathVariable int id) {
        log.info("GET: /api/v1/{}/complete", id);
        orderService.completeOrder(id);
        return BaseResponseBody.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail("Заказ успешно выполнен")
                .build();
    }

    /**
     *  Возвращает список всех заказов.
     *
     * @return {@link ResponseBodyWithData} со списком заказов
     */
    @GetMapping
    public ResponseBodyWithData getAllOrders() {
        log.info("GET: /api/v1/orders");
        List<Order> orders = orderService.getAllOrders();
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail("Список заказов успешно получен")
                .data(Map.of("orders", orders))
                .build();
    }

}
