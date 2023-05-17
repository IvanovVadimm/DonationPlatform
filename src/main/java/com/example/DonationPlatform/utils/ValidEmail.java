package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** Validation by email **/
public class ValidEmail implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return value.matches(regex);
    }
}
