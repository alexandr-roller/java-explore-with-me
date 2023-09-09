package ru.practicum.ewm.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequestRangeValidator.class)
public @interface RequestRange {
    String message() default "Wrong range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
