package ru.kaznacheev.restaurant.waiterservice.controller;

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
import ru.kaznacheev.restaurant.common.dto.response.ExceptionResponse;
import ru.kaznacheev.restaurant.common.dto.response.OrderStatusResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateOrderRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.OrderShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.service.OrderService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 */
@Tag(name = "Orders controller", description = "Обрабатывает запросы, связанные с заказами")
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Создает новый заказ.
     *
     * @param request DTO c информацией о новом заказе
     * @return {@link OrderResponse} с информацией о созданном заказе
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
                    schema = @Schema(implementation = OrderResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "id": 5,
                                        "status": "IN_PROGRESS",
                                        "createdAt": "2025-01-01T00:00:00.000Z",
                                        "waiterId" : 1,
                                        "tableNumber": "3a",
                                        "cost": 1920,
                                        "dishes": [
                                            {
                                                "dishId": 1,
                                                "name": "Паста карбонара",
                                                "amount": 1
                                            },
                                            {
                                                "dishId": 2,
                                                "name": "Борщ с пампушками",
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
                                            "dishes": [
                                                "Состав заказа не должен быть пустым"
                                            ],
                                            "waiterId": [
                                                "Идентификатор официанта не должен быть пустым"
                                            ],
                                            "tableNumber": [
                                                "Номер стола не должен быть пустым"
                                            ]
                                        }
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "404", description = "Официант с указанным идентификатором не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Официант с идентификатором 22 не найден"
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "409", description = "Некорректный состав заказа",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
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
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        log.debug("POST | /api/v1/orders | id официанта: {}, состав заказа: {}", request.getWaiterId(), request.getDishes());
        return orderService.createOrder(request);
    }

    /**
     * Возвращает информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return {@link OrderResponse} с информацией о заказе
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
    public OrderResponse getOrder(@PathVariable Long id) {
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
                            @ExampleObject(name = "Пустой список", value = "[]", description = "Пустой список, если заказов нет")}
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
    @ApiResponse(responseCode = "200", description = "Информация о статусе заказе успешно получена", useReturnTypeSchema = true)
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

}
