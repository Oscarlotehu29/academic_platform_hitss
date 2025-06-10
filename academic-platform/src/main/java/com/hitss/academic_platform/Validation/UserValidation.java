package com.hitss.academic_platform.Validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hitss.academic_platform.dto.UserDto;
import com.hitss.academic_platform.services.UserService;

@Component
public class UserValidation implements Validator{
	private final String MESSAGE_FIELD_REQUIRED = " is required.";
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserService.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		
		UserDto user = (UserDto) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getEmail() == null || user.getEmail().isBlank()) errors.rejectValue("email", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getPassword() == null || user.getPassword().isBlank()) errors.rejectValue("password", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getUserName() == null || user.getUserName().isBlank()) errors.rejectValue(" userName", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getName() == null || user.getName().isBlank()) errors.rejectValue("name", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getFatherLastName() == null || user.getFatherLastName().isBlank()) errors.rejectValue("fatherLastName", null, MESSAGE_FIELD_REQUIRED);
		
		if(user.getPassword().length() < 8) errors.rejectValue("password", null, " must have a minimum of 8 characters.");
		
		if(user.getUserName().length() > 15) errors.rejectValue("userName", null, " must have a maximum of 15 characters.");
		
		if(user.getName().length() > 25) errors.rejectValue("name", null, " must have a maximum of 25 characters");
		
		if(user.getFatherLastName().length() > 25) errors.rejectValue("fatherLastName", null, " must have a maximum of 25 characters");
		
		if(user.getRoleName() == null || user.getRoleName().isBlank()) errors.rejectValue("roleId", null, " must have a value");

		
		//		if(userService.existsByEmail(user.getEmail())) errors.rejectValue("email", null, " is already associated with an existing user.");

		//		

		//		if(userService.existsByUserName(user.getUserName())) errors.rejectValue("userName", null, " is already a user with an existing user.");
	}
}
