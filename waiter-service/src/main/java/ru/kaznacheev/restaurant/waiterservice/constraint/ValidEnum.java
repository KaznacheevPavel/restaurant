package ru.kaznacheev.restaurant.waiterservice.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.restaurant.waiterservice.constraint.validator.EnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки соответствия значения поля одному из значений перечисления.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Неверное значение для перечисления";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
