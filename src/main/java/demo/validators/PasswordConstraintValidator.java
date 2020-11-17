package demo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
	public static final int MIN_LENGTH = 5;
	public static final String ILLEGAL_PASSWORD_MESSAGE = "Password must be at least 5 characters long and contain at least 1 digit.";
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.length() >= MIN_LENGTH && value.matches(".*\\d.*")) {
			return true;
		}
		context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
          ILLEGAL_PASSWORD_MESSAGE)
          .addConstraintViolation();
        return false;
	}

}
