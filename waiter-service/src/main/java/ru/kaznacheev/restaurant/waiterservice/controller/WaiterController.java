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
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateWaiterRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterResponse;
import ru.kaznacheev.restaurant.waiterservice.dto.response.WaiterShortInfoResponse;
import ru.kaznacheev.restaurant.waiterservice.service.WaiterService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с официантами.
 */
@Tag(name = "Waiters controller", description = "Обрабатывает запросы, связанные с официантами")
@RestController
@RequestMapping("/api/v1/waiters")
@Slf4j
@RequiredArgsConstructor
public class WaiterController {

    private final WaiterService waiterService;

    /**
     * Создает нового официанта.
     *
     * @param request DTO с информацией о новом официанте
     * @return {@link WaiterResponse} с информацией о созданном официанте
     */
    @Operation(
            summary = "Создает нового официанта",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о новом официанте",
                    required = true, useParameterTypeSchema = true
            )
    )
    @ApiResponse(responseCode = "201", description = "Официант успешно создан", useReturnTypeSchema = true)
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
                                            "sex": [
                                                "Пол официанта не должен быть пустым",
                                                "Пол официанта должен быть MALE или FEMALE"
                                            ],
                                            "name": [
                                                "Имя официанта не должно быть пустым",
                                                "Имя официанта не должно быть меньше 1 символа"
                                            ]
                                        }
                                    }
                                    """
                    )}
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaiterResponse createWaiter(@RequestBody CreateWaiterRequest request) {
        log.debug("POST | /api/v1/waiters | Имя официанта: {}", request.getName());
        return waiterService.createWaiter(request);
    }

    /**
     * Возвращает информацию об официанте по его идентификатору.
     *
     * @param id Идентификатор официанта
     * @return {@link WaiterResponse} с информацией об официанте
     */
    @Operation(
            summary = "Возвращает информацию об официанте по его идентификатору",
            parameters = @Parameter(name = "id", description = "Идентификатор официанта")
    )
    @ApiResponse(responseCode = "200", description = "Информация об официанте успешно получена", useReturnTypeSchema = true)
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
    @GetMapping("/{id}")
    public WaiterResponse getWaiter(@PathVariable Long id) {
        log.debug("GET | /api/v1/waiters/{}", id);
        return waiterService.getWaiterById(id);
    }

    /**
     * Возвращает список с краткой информацией о всех официантах.
     *
     * @return {@link List} {@link WaiterShortInfoResponse} с краткой информацией об официанте
     */
    @Operation(summary = "Возвращает список с краткой информацией о всех официантах")
    @ApiResponse(responseCode = "200", description = "Список официантов успешно получен",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = WaiterShortInfoResponse.class)),
                    examples = {@ExampleObject(name = "Список с краткой информацией об официантах",
                            value = """
                                    [
                                        {
                                            "id": 1,
                                            "name": "Иван",
                                        },
                                        {
                                            "id": 2,
                                            "name": "Николай",
                                        }
                                    ]
                                    """
                    ),
                            @ExampleObject(name = "Пустой список", value = "[]", description = "Пустой список, если официантов нет")}
            )
    )
    @GetMapping
    public List<WaiterShortInfoResponse> getAllWaiters() {
        log.debug("GET | /api/v1/waiters");
        return waiterService.getAllWaiters();
    }

}
