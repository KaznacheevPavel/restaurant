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
import ru.kaznacheev.restaurant.common.dto.response.BaseResponseBody;
import ru.kaznacheev.restaurant.common.dto.response.ResponseBodyWithData;
import ru.kaznacheev.restaurant.common.dto.response.ResponseTitle;
import ru.kaznacheev.restaurant.kitchenservice.dto.NewOrderDto;
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
public class OrderController {

    private final OrderService orderService;

    /**
     *  Создает новый заказ.
     *
     * @param newOrderDto DTO, содержащий информацию о новом заказе
     * @return {@link BaseResponseBody} с информацией о создании заказа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponseBody createOrder(@RequestBody NewOrderDto newOrderDto) {
        orderService.createOrder(newOrderDto);
        return BaseResponseBody.builder()
                .title(ResponseTitle.CREATED.name())
                .status(ResponseTitle.CREATED.getStatus())
                .detail("Заказ успешно создан")
                .build();
    }

    /**
     * Отклоняет заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponseBody} с информацией об отмене заказа
     */
    @PostMapping("/{id}/reject")
    public BaseResponseBody rejectOrder(@PathVariable Long id) {
        orderService.rejectOrder(id);
        return BaseResponseBody.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail("Заказ успешно отменен")
                .build();
    }

    /**
     * Завершает заказ по идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link BaseResponseBody} с информацией о завершении заказа
     */
    @PostMapping("/{id}/complete")
    public BaseResponseBody completeOrder(@PathVariable Long id) {
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
        List<Order> orders = orderService.getAllOrders();
        return ResponseBodyWithData.builder()
                .title(ResponseTitle.SUCCESS.name())
                .status(ResponseTitle.SUCCESS.getStatus())
                .detail("Список заказов успешно получен")
                .data(Map.of("orders", orders))
                .build();
    }

}
