package com.example.DonationPlatform.annotation;

import com.example.DonationPlatform.utils.ValidVisaCardNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Constraint(validatedBy = ValidVisaCardNumber.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVisaCardNumberAnnotation {

    String message() default "First character not +";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

