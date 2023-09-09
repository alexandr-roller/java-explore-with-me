package ru.practicum.ewm.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class RequestRangeValidator implements ConstraintValidator<RequestRange, Object[]> {
    @Override
    public void initialize(RequestRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext context) {
        if (objects[0] == null || objects[1] == null) {
            return true;
        }

        if (!(objects[0] instanceof LocalDateTime) || !(objects[1] instanceof LocalDateTime)) {
            return false;
        }

        LocalDateTime start = (LocalDateTime) objects[0];
        LocalDateTime end = (LocalDateTime) objects[1];

        return start.isBefore(end) || start.isEqual(end);
    }
}
