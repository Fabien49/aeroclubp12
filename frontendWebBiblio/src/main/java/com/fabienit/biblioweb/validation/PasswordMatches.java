package com.fabienit.biblioweb.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * PasswordMatches
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Les mots de passes ne correspondent pas.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}