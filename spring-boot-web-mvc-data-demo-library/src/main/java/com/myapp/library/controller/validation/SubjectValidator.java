package com.myapp.library.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.myapp.library.entity.Subject;

@Component
public class SubjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Subject.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Subject subject = (Subject) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subtitle", "NotEmpty.subject");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationInHrs", "NotEmpty.subject");

		if (subject.getDurationInHrs() == 0) {
			errors.rejectValue("durationInHrs", "NotEmpty.subject.durationInHrs");
		}

	}

}
