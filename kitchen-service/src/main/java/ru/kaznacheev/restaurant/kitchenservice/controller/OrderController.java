package ru.kaznacheev.restaurant.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.restaurant.common.dto.NewOrderDto;
import ru.kaznacheev.restaurant.common.dto.response.BaseResponse;
import ru.kaznacheev.restaurant.common.dto.response.ResponseWithData;
import ru.kaznacheev.restaurant.common.entity.Order;
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
     * @return {@link BaseResponse} с информацией о создании заказа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse acceptOrder(@RequestBody NewOrderDto newOrderDto) {
        log.info("POST: /api/v1/orders {}", newOrderDto.hashCode());
        orderService.acceptOrder(newOrderDto);
        return BaseResponse.builder()
                .title("CREATED")
                .status(HttpStatus.CREATED.value())
                .detail("Заказ успешно принят")
                .build();
    }

    /**
     * Отклоняет заказ по указанному идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponse} с информацией об отмене заказа
     */
    @PostMapping("/{id}/reject")
    public BaseResponse rejectOrder(@PathVariable int id) {
        log.info("GET: /api/v1/{}/reject", id);
        orderService.rejectOrder(id);
        return BaseResponse.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Заказ успешно отменен")
                .build();
    }

    /**
     * Завершает заказ по указанному идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponse} с информацией о завершении заказа
     */
    @PostMapping("/{id}/complete")
    public BaseResponse completeOrder(@PathVariable int id) {
        log.info("GET: /api/v1/{}/complete", id);
        orderService.completeOrder(id);
        return BaseResponse.builder()
                .title("SUCCESS")
                .status(HttpStatus.OK.value())
                .detail("Заказ успешно выполнен")
                .build();
    }

    /**
     *  Возвращает список всех заказов.
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

}
