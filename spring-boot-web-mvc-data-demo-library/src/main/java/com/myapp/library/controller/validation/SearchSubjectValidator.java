package com.myapp.library.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SearchSubjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SubjectSearchCriteria.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SubjectSearchCriteria subjectSearchCriteria = (SubjectSearchCriteria) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationStart", "NotEmpty.subjectSearchCriteria");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationEnd", "NotEmpty.subjectSearchCriteria");

		if (subjectSearchCriteria != null && subjectSearchCriteria.getDurationStart() == 0) {
			errors.rejectValue("durationStart", "NotEmpty.subjectSearchCriteria.durationStart");
		}

		if (subjectSearchCriteria != null && subjectSearchCriteria.getDurationEnd() == 0) {
			errors.rejectValue("durationEnd", "NotEmpty.subjectSearchCriteria.durationEnd");
		}

	}

}
