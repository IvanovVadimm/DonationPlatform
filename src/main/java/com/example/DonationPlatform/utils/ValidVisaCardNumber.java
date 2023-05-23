package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.VisaCardNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validation by type of Visa card start with 4 and have any 15 characters after
 **/
public class ValidVisaCardNumber implements ConstraintValidator<VisaCardNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regexForCardNumber = "^4[0-9]{15}$";
        return value.matches(regexForCardNumber);
    }
}