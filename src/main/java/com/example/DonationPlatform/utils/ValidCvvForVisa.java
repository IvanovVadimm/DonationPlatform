package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.CvvForVisa;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validation by any three numbers
 **/
public class ValidCvvForVisa implements ConstraintValidator<CvvForVisa, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "[0-9]{3}$";
        return value.matches(regex);
    }
}