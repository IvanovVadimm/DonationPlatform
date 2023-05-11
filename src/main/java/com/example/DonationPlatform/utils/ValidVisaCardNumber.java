package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.ValidVisaCardNumberAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidVisaCardNumber implements ConstraintValidator<ValidVisaCardNumberAnnotation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regexForCardNumber = "^4[0-9]{15}$";
        return value.matches(regexForCardNumber);
    }
}
