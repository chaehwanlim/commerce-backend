package chlim.commercebackend.domain.common;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class SelfValidating<T> {

	static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@SuppressWarnings("unchecked")
	protected void validateAndIfViolatedThrow() {
		Set<ConstraintViolation<T>> violations = validator.validate((T) this);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}
