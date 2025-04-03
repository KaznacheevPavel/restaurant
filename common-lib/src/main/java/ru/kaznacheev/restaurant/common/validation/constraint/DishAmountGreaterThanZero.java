package ru.kaznacheev.restaurant.common.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.restaurant.common.validation.DishAmountGreaterThanZeroValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для валидации количества блюд в заказе.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DishAmountGreaterThanZeroValidator.class)
public @interface DishAmountGreaterThanZero {
    String message() default "Количество порций должно быть больше нуля";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
