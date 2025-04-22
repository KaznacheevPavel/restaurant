package ru.kaznacheev.restaurant.waiterservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreatePaymentRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.PaymentResponse;
import ru.kaznacheev.restaurant.waiterservice.service.PaymentService;

/**
 * Контроллер для обработки запросов, связанных с платежами.
 */
@Tag(name = "Payments controller", description = "Обрабатывает запросы, связанные с платежами")
@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Создает новый платеж.
     *
     * @param request DTO с информацией о новом платеже
     * @return {@link PaymentResponse} с информацией о созданном платеже
     */
    @Operation(
            summary = "Создает новый платеж",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о новом платеже",
                    required = true, useParameterTypeSchema = true
            )
    )
    @ApiResponse(responseCode = "201", description = "Платеж успешно создан", useReturnTypeSchema = true)
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
                                            "orderId": [
                                                "Идентификатор заказа не должен быть пустым"
                                            ],
                                            "sum": [
                                                "Сумма платежа должна быть положительным числом",
                                                "Сумма платежа может содержать 6 целых и 2 десятичных знака"
                                            ],
                                            "type": [
                                                "Тип платежа не должен быть пустым",
                                                "Тип платежа должен быть CARD или CASH"
                                            ]
                                        }
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
    @ApiResponse(responseCode = "409", description = "Некорректный платеж",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            name = "Неверная сумма оплаты",
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Сумма оплаты 1200 не совпадает с суммой заказа 1541.99",
                                    }
                                    """
                    ), @ExampleObject(
                            name = "Заказ не может быть оплачен",
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Заказ может быть оплачен только в статусе COOKED",
                                    }
                                    """
                    )}
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@RequestBody CreatePaymentRequest request) {
        log.debug("POST | /api/v1/payments | id заказа: {}", request.getOrderId());
        return paymentService.createPayment(request);
    }

    /**
     * Возвращает информацию о платеже по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа
     * @return {@link PaymentResponse} с информацией о платеже
     */
    @Operation(
            summary = "Возвращает информацию о платеже по идентификатору заказа",
            parameters = @Parameter(name = "orderId", description = "Идентификатор заказа")
    )
    @ApiResponse(responseCode = "200", description = "Информация о платеже успешно получена",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "Платеж с указанным идентификатором заказа не найден",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Платеж с идентификатором 54 не найден"
                                    }
                                    """
                    )}
            )
    )
    @GetMapping("/{orderId}")
    public PaymentResponse getPayment(@PathVariable Long orderId) {
        log.debug("GET | /api/v1/payments/{}", orderId);
        return paymentService.getPaymentByOrderId(orderId);
    }

}
