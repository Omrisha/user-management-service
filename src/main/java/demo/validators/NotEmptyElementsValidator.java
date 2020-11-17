package demo.validators;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyElementsValidator implements ConstraintValidator<NotEmptyElements, String[]> {

    @Override
    public void initialize(NotEmptyElements notEmptyFields) {
    }

    @Override
    public boolean isValid(String[] elements, ConstraintValidatorContext context) {
        return !Arrays.asList(elements).contains("");
    }
}
