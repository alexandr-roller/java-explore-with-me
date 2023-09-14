package ru.practicum.ewm.main.event.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDate {
    String message() default "Field 'eventDate' must be after now plus 2 hours.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
