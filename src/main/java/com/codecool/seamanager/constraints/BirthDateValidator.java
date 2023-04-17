package com.codecool.seamanager.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
		if (date == null) {
			return true;
		}
		LocalDate now = LocalDate.now();
		if (now.getYear() - date.getYear() < 0) return true; //handled by @Past constraint validator
		return now.getYear() - date.getYear() >= 18;
	}
}
