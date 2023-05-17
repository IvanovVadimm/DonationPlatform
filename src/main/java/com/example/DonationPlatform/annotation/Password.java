package com.example.DonationPlatform.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Constraint(validatedBy = com.example.DonationPlatform.utils.ValidPassword.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password must contain 8 to 16 characters including at least one lowercase letter, uppercase letter and number!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}