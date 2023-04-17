package com.codecool.seamanager.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

	private Class<? extends Enum<?>> enumClass;

	@Override
	public void initialize(ValidEnum annotation) {
		enumClass = annotation.enumClass();
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			if (enumValue.name().equals(value.name())) {
				return true;
			}
		}
		return false;
	}
}