package com.example.DonationPlatform.utils;

import com.example.DonationPlatform.annotation.ExpireDateVisaCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;

/** Validation by expire date **/
public class ValidExpireDateVisaCard implements ConstraintValidator<ExpireDateVisaCard, Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        Date nowDate = Date.valueOf(LocalDate.now());
        return nowDate.before(value);
    }
}
