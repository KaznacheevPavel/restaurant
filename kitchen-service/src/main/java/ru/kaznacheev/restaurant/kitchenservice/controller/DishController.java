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
import ru.kaznacheev.restaurant.common.dto.request.CreateKitchenDishRequest;
import ru.kaznacheev.restaurant.common.dto.response.ExceptionResponse;
import ru.kaznacheev.restaurant.common.dto.response.KitchenDishResponse;
import ru.kaznacheev.restaurant.kitchenservice.dto.request.AddDishBalanceRequest;
import ru.kaznacheev.restaurant.kitchenservice.service.DishService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с блюдами.
 */
@Tag(name = "Dishes controller", description = "Обрабатывает запросы, связанные с блюдами")
@RestController
@RequestMapping("/api/v1/dishes")
@Slf4j
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    /**
     * Создает новое блюдо.
     *
     * @param request DTO с информацией о новом блюде
     * @return {@link KitchenDishResponse} с информацией о созданном блюде
     */
    @Operation(
            summary = "Создает новое блюдо",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о новом блюде",
                    required = true, useParameterTypeSchema = true
            )
    )
    @ApiResponse(responseCode = "201", description = "Блюдо успешно создано", useReturnTypeSchema = true)
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
                                        "composition": [
                                          "Состав блюда не должен быть пустым"
                                        ],
                                        "id": [
                                          "Идентификатор блюда должен быть больше 0"
                                        ],
                                        "shortName": [
                                          "Сокращенное название блюда не должно быть пустым"
                                        ]
                                      }
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "409", description = "Название блюда занято",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "CONFLICT",
                                        "status": 409,
                                        "detail": "Блюдо с названием Борщ уже существует"
                                    }
                                    """
                    )}
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDishResponse createDish(@RequestBody CreateKitchenDishRequest request) {
        log.debug("POST | /api/v1/dishes | id блюда: {}", request.getId());
        return dishService.createDish(request);
    }

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    @Operation(
            summary = "Возвращает информацию о блюде по его идентификатору",
            parameters = @Parameter(name = "id", description = "Идентификатор блюда")
    )
    @ApiResponse(responseCode = "200", description = "Информация о блюде успешно получена", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "Блюдо с указанным идентификатором не найдено",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Блюдо с идентификатором 53 не найдено"
                                    }
                                    """
                    )}
            )
    )
    @GetMapping("/{id}")
    public KitchenDishResponse getDish(@PathVariable Long id) {
        log.debug("GET | /api/v1/dishes/{}", id);
        return dishService.getDishById(id);
    }

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link KitchenDishResponse} с информацией о блюде
     */
    @Operation(summary = "Возвращает список с информацией о всех блюдах")
    @ApiResponse(responseCode = "200", description = "Список блюд успешно получен",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = KitchenDishResponse.class)),
                    examples = {@ExampleObject(name = "Список с информацией о блюдах",
                            value = """
                                    [
                                        {
                                            "id": 1,
                                            "balance": 3,
                                            "shortName": "Карбонара",
                                            "composition": "Паста, панчетта, яйца, сыр"
                                        },
                                        {
                                            "id": 2,
                                            "balance": 25,
                                            "shortName": "Борщ",
                                            "composition": "Свекла, капуста, картофель, морковь, лук, мясо, зелень"
                                        }
                                    ]
                                    """
                    ),
                            @ExampleObject(name = "Пустой список", value = "[]", description = "Пустой список, если блюд нет")}
            )
    )
    @GetMapping
    public List<KitchenDishResponse> getAllDishes() {
        log.debug("GET | /api/v1/dishes");
        return dishService.getAllDishes();
    }

    /**
     * Добавляет порции для блюда.
     *
     * @param id Идентификатор блюда
     * @param request DTO, с информацией о количестве порций
     * @return {@link KitchenDishResponse} с информацией о блюде
     */
    @Operation(
            summary = "Добавляет порции для блюда",
            parameters = @Parameter(name = "id", description = "Идентификатор блюда"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Информация о количестве порций для добавления",
                    required = true, useParameterTypeSchema = true
            )
    )
    @ApiResponse(responseCode = "200", description = "Порции для блюда успешно добавлены", useReturnTypeSchema = true)
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
                                            "balance": [
                                                "Количество порций должно быть положительным числом"
                                            ]
                                        }
                                    }
                                    """
                    )}
            )
    )
    @ApiResponse(responseCode = "404", description = "Блюдо с указанным идентификатором не найдено",
            content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                        "title": "NOT_FOUND",
                                        "status": 404,
                                        "detail": "Блюдо с идентификатором 53 не найдено"
                                    }
                                    """
                    )}
            )
    )
    @PostMapping("/{id}/balance")
    public KitchenDishResponse addDishBalance(@PathVariable Long id, @RequestBody AddDishBalanceRequest request) {
        log.debug("POST | /api/v1/dishes/{}/balance", id);
        return dishService.addDishBalance(id, request);
    }

}
