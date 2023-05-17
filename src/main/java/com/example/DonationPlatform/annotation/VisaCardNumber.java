package com.example.DonationPlatform.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Constraint(validatedBy = com.example.DonationPlatform.utils.ValidVisaCardNumber.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisaCardNumber {
    String message() default "This number of card is not for Visa cards";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}