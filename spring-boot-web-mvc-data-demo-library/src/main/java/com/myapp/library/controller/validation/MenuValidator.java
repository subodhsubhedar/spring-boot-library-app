package com.myapp.library.controller.validation;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.myapp.library.menu.MainMenuModel;

@Component
public class MenuValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MainMenuModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MainMenuModel menuModel = (MainMenuModel) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "menuIndex", "NotEmpty.menu");

		List<Integer> criteriaMenuList = Arrays.asList(3, 4, 5, 6);

		if (menuModel != null && (criteriaMenuList.contains(menuModel.getMenuIndex()))
				&& (StringUtils.isEmpty(menuModel.getMenuCriteria()))) {
			errors.rejectValue("menuCriteria", "NotEmpty.menu.menuCriteria");
		}
	}

}
