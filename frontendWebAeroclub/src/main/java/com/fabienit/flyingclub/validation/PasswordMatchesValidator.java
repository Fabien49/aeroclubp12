package com.fabienit.flyingclub.validation;

import com.fabienit.flyingclub.model.dto.RegisteredUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * PasswordMatchesValidator
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisteredUserDto user = (RegisteredUserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}