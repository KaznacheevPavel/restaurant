package ru.kaznacheev.restaurant.kitchenservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenOrderRequest;
import ru.kaznacheev.restaurant.common.dto.response.ExceptionResponse;
import ru.kaznacheev.restaurant.common.dto.response.KitchenOrderResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.kitchenservice.service.OrderService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 */
@Tag(name = "Orders controller", description = "Контроллер для обработки запросов, связанных с заказами")
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Создает новый заказ.
     *
     * @param request DTO, с информацией о новом заказе
     * @return {@link KitchenOrderResponse} с информацией о созданном заказе
     */
    @Operation(
            summary = "Создает новый заказ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о новом заказе",
                    required = true, useParameterTypeSchema = true
            )
    )
    @ApiResponse(responseCode = "201", description = "Заказ успешно создан",
            content = @Content(
                    schema = @Schema(implementation = KitchenOrderResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "id": 3,
                                        "waiterOrderId": 3,
                                        "status": "IN_PROGRESS",
                                        "createdAt": "2025-01-01T00:00:00.000Z",
                                        "dishes": [
                                            {
                                                "dishId": 1,
                                                "name": "Карбонара",
                                                "amount": 1
                                            },
                                            {
                                                "dishId": 2,
                                                "name": "Борщ",
                                                "amount": 3
                                            }
                                        ]
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "VALIDATION_EXCEPTION",
                                        "status": 400,
                                        "detail": "Ошибка валидации",
                                        "data": {
                                            "waiterOrderId": [
                                                "Идентификатор заказа у официантов не должен быть пустым"
                                            ],
                                            "<map value>": [
                                                "Количество порций должно быть больше 0"
                                            ]
                                        }
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "409", description = "Некорректный состав заказа",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            name = "Недостаточное количество порций",
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Некорректный состав заказа",
                                        "data": [
                                            "Идентификаторы блюд с недостаточным количеством порций" : [
                                                1
                                            ]
                                        ]
                                    }
                                    """
                    ), @ExampleObject(
                            name = "Не найденные блюда",
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Некорректный состав заказа",
                                        "data": [
                                            "Список не найденных блюд" : [
                                                5
                                            ]
                                        ]
                                    }
                                    """
                    )}
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOrderResponse createOrder(@RequestBody CreateKitchenOrderRequest request) {
        log.debug("POST | /api/v1/orders | id заказа: {}", request.getWaiterOrderId());
        return orderService.createOrder(request);
    }

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link KitchenOrderResponse} с информацией о заказе
     */
    @Operation(
            summary = "Возвращает информацию о заказе по его идентификатору",
            parameters = @Parameter(name = "id", description = "Идентификатор заказа")
    )
    @ApiResponse(responseCode = "200", description = "Информация о заказе успешно получена", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "Заказ с указанным идентификатором не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Заказ с идентификатором 21 не найден"
                                    }
                                    """
                    )}
            )
    )
    @GetMapping("/{id}")
    public KitchenOrderResponse getOrder(@PathVariable Long id) {
        log.debug("GET | /api/v1/orders/{}", id);
        return orderService.getOrderById(id);
    }

    /**
     * Возвращает список с краткой информацией о всех заказах.
     *
     * @return {@link List} {@link OrderShortInfoResponse} с краткой информацией о заказе
     */
    @Operation(summary = "Возвращает список с краткой информацией о всех заказах")
    @ApiResponse(responseCode = "200", description = "Список заказов успешно получен",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = OrderShortInfoResponse.class)),
                    examples = {@ExampleObject(name = "Список с краткой информацией о заказах",
                            value = """
                                    [
                                        {
                                            "id": 1,
                                            "waiterOrderId": 1,
                                            "status": "COMPLETED",
                                            "createdAt": "2025-02-03T12:43:44.000Z"
                                        },
                                        {
                                            "id": 2,
                                            "waiterOrderId": 2,
                                            "status": "IN_PROGRESS",
                                            "createdAt": "2025-02-03T14:26:12.000Z"
                                        }
                                    ]
                                    """
                    ),
                            @ExampleObject(name = "Пустой список", value = "[]",
                                    description = "Пустой список, если заказов нет")}
            )
    )
    @GetMapping
    public List<OrderShortInfoResponse> getAllOrders() {
        log.debug("GET | /api/v1/orders");
        return orderService.getAllOrders();
    }

    /**
     * Возвращает информацию о статусе заказа по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @Operation(
            summary = "Возвращает информацию о статусе заказе по его идентификатору",
            parameters = @Parameter(name = "id", description = "Идентификатор заказа")
    )
    @ApiResponse(responseCode = "200", description = "Информация о статусе заказе успешно получена",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "Заказ с указанным идентификатором не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Заказ с идентификатором 32 не найден"
                                    }
                                    """
                    )}
            )
    )
    @GetMapping("/{id}/status")
    public OrderStatusResponse getOrderStatus(@PathVariable Long id) {
        log.debug("GET | /api/v1/orders/{}/status", id);
        return orderService.getOrderStatus(id);
    }

    /**
     * Завершает приготовление заказа.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @Operation(
            summary = "Завершает приготовление заказа",
            parameters = @Parameter(name = "id", description = "Идентификатор заказа")
    )
    @ApiResponse(responseCode = "200", description = "Заказ успешно приготовлен", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "Заказ с указанным идентификатором не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Заказ с идентификатором 32 не найден"
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "409", description = "Заказ не может быть приготовлен",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Заказ может быть приготовлен только в статусе IN_PROGRESS"
                                    }
                                    """
                    )}
            )
    )
    @PostMapping("/{id}/complete")
    public OrderStatusResponse completeOrder(@PathVariable Long id) {
        log.debug("POST | /api/v1/orders/{}/complete", id);
        return orderService.completeOrder(id);
    }

    /**
     * Отменяет заказ.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderStatusResponse} с информацией о статусе заказа
     */
    @Operation(
            summary = "Отменяет заказ",
            parameters = @Parameter(name = "id", description = "Идентификатор заказа")
    )
    @ApiResponse(responseCode = "200", description = "Заказ успешно отменен",
            content = @Content(
                    schema = @Schema(implementation = OrderStatusResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "id": 12,
                                        "status": "REJECTED"
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "404", description = "Заказ с указанным идентификатором не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Заказ с идентификатором 32 не найден"
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "409", description = "Заказ не может быть отменен",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Заказ может быть отменен только в статусе IN_PROGRESS"
                                    }
                                    """
                    )}
            )
    )
    @PostMapping("/{id}/reject")
    public OrderStatusResponse rejectOrder(@PathVariable Long id) {
        log.debug("POST | /api/v1/orders/{}/reject", id);
        return orderService.rejectOrder(id);
    }

}
