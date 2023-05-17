package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.Login;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** Validation by entering phone number by start with 8029, 8033 or 8044 and having seven any numbers after **/
public class ValidLogin implements ConstraintValidator<Login, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex1 = "^8029\\d{7}$";
        String regex2 = "^8033\\d{7}$";
        String regex3 = "^8044\\d{7}$";
        return (value.matches(regex1) || value.matches(regex2) || value.matches(regex3));

    }
}
