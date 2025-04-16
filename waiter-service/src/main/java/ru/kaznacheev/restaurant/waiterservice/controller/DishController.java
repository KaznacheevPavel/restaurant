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
import ru.kaznacheev.restaurant.waiterservice.dto.request.CreateDishRequest;
import ru.kaznacheev.restaurant.waiterservice.dto.response.DishResponse;
import ru.kaznacheev.restaurant.waiterservice.service.DishService;

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
     * @return {@link DishResponse} с информацией о созданном блюде
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
                                            "cost": [
                                                "Стоимость блюда может содержать 5 целых и 2 десятичных знака",
                                                "Стоимость блюда должна быть положительным числом"
                                            ],
                                            "composition": [
                                                "Состав блюда не должен быть пустым"
                                            ],
                                            "name": [
                                                "Название блюда не должно быть пустым"
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
    public DishResponse createDish(@RequestBody CreateDishRequest request) {
        log.debug("POST | /api/v1/dishes | Название блюда: {}", request.getName());
        return dishService.createDish(request);
    }

    /**
     * Возвращает информацию о блюде по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return {@link DishResponse} с информацией о блюде
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
    public DishResponse getDish(@PathVariable Long id) {
        log.debug("GET | /api/v1/dishes/{}", id);
        return dishService.getDishById(id);
    }

    /**
     * Возвращает список с информацией о всех блюдах.
     *
     * @return {@link List} {@link DishResponse} с информацией о блюде
     */
    @Operation(summary = "Возвращает список с информацией о всех блюдах")
    @ApiResponse(responseCode = "200", description = "Список блюд успешно получен",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = DishResponse.class)),
                    examples = {@ExampleObject(name = "Список с информацией о блюдах",
                            value = """
                                    [
                                        {
                                            "id": 1,
                                            "name": "Паста карбонара",
                                            "cost": 540
                                        },
                                        {
                                            "id": 2,
                                            "name": "Борщ с пампушками",
                                            "cost": 460
                                        }
                                    ]
                                    """
                    ),
                            @ExampleObject(name = "Пустой список", value = "[]", description = "Пустой список, если блюд нет")}
            )
    )
    @GetMapping
    public List<DishResponse> getAllDishes() {
        log.debug("GET | /api/v1/dishes");
        return dishService.getAllDishes();
    }

}
