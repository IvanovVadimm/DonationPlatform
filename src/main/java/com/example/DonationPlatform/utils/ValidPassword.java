package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** Validation by entering at least one uppercase letter, one lowercase letter and one number **/
public class ValidPassword implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return value.matches(regex);
    }
}
