package ru.kaznacheev.restaurant.waiterservice.constraint.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.restaurant.waiterservice.constraint.ValidEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * Валидатор для проверки соответствия строки одному из значения перечисления.
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Enum<?>[] values;

    /**
     * Инициализирует валидатор.
     *
     * @param constraintAnnotation Аннотация, содержащая параметры валидации
     */
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        values = constraintAnnotation.enumClass().getEnumConstants();
    }

    /**
     * Проверяет, что переданное значение соответствует одному из значений перечисления.
     *
     * @param value Значение для проверки
     * @param constraintValidatorContext Контекст валидации
     * @return {@code true}, если значение соответствует перечислению, иначе {@code false}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return Arrays.stream(values)
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }

}
