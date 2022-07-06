package org.apirest.example.validation.constraintvalidation;

import org.apirest.example.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {


    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {

        return list != null && !list.isEmpty();
    }
}
