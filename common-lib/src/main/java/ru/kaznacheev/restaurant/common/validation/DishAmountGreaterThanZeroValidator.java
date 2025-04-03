package ru.kaznacheev.restaurant.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.restaurant.common.validation.constraint.DishAmountGreaterThanZero;

import java.util.Map;

/**
 * Валидатор для проверки количества блюд в заказе.
 */
public class DishAmountGreaterThanZeroValidator implements ConstraintValidator<DishAmountGreaterThanZero, Map<?, Long>> {

    /**
     * Проверяет, что количество блюд в заказе больше нуля.
     *
     * @param dishes Блюда в заказе
     * @param constraintValidatorContext контекст валидации
     * @return {@code true}, если количество блюд больше нуля, иначе {@code false}
     */
    @Override
    public boolean isValid(Map<?, Long> dishes, ConstraintValidatorContext constraintValidatorContext) {
        return dishes.values().stream().allMatch(amount -> amount > 0);
    }

}
