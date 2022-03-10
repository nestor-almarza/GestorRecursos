package com.gestor.utils.validators.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gestor.domains.User;
import com.gestor.utils.validators.IUserValidator;
import com.gestor.utils.validators.errors.ValidationError;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("userValidatorImpl")
public class UserValidatorImpl implements IUserValidator {

	@Override
	public ValidationError validateGenerateUser(User user) {
		
		ValidationError validationError = new ValidationError();
		
		if(user == null) {
			validationError.addFieldError("null", "User doesn't exist.");
			
			return validationError;
		}
		
		//FirstName
		final String FIRST_NAME = user.getFirstName();
		
		String regexFirstName = "^[A-Z\\u00C0-\\u017F\\s]+$";
		Pattern patternFirstName = Pattern.compile(regexFirstName, Pattern.CASE_INSENSITIVE);
		Matcher matcherFirstName = patternFirstName.matcher(FIRST_NAME);

		if(!matcherFirstName.matches()) {
			validationError.addFieldError("firstName", "First name contains invalid characters");
		}

		if(StringUtils.isBlank(FIRST_NAME))
			validationError.addFieldError("firstName", "First name cannot be empty.");
		else {
			if(FIRST_NAME.length() < 3 || FIRST_NAME.length() > 40)
				validationError.addFieldError("firstName", "First name must have between 3 and 40 characters");
		}
		
		
		//LastName
		final String LAST_NAME = user.getLastName();

		String regexLastName = "^[A-Z\\u00C0-\\u017F\\s-]+$";
		Pattern patternLastName = Pattern.compile(regexLastName, Pattern.CASE_INSENSITIVE);
		Matcher matcherLastName = patternLastName.matcher(LAST_NAME);

		if(!matcherLastName.matches()) {
			validationError.addFieldError("firstName", "Last name contains invalid characters");
		}
		
		if(StringUtils.isBlank(LAST_NAME))
			validationError.addFieldError("lastName", "Last name cannot be empty.");
		else {
			if(LAST_NAME.length() < 3 || LAST_NAME.length() > 40)
				validationError.addFieldError("lastName", "Last name must have between 3 and 40 characters");
		}
		
		
		//Email
		final String EMAIL = user.getEmail();
		
		String regexEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern patternEmail = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
		Matcher matcherEmail = patternEmail.matcher(EMAIL);
		
		if(!matcherEmail.matches())
			validationError.addFieldError("email", "Email has an incorrect format.");
		

		//Password
		final String PASSWORD = user.getPassword();

		String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,64}$";
		Pattern patternPassword = Pattern.compile(regexPassword, Pattern.CASE_INSENSITIVE);
		Matcher matcherPassword = patternPassword.matcher(PASSWORD);

		if(!matcherPassword.matches())
			validationError.addFieldError("PASSWORD", "A password must contain regular and capital letters and a number.");

		return validationError;
	}

	@Override
	public ValidationError validateEditUser(User user) {
		
		ValidationError validationError = new ValidationError();
		
		if(user == null) {
			validationError.addFieldError("null", "User doesn't exist.");
			
			return validationError;
		}
		
		//FirstName
		final String FIRST_NAME = user.getFirstName();
		
		String regexFirstName = "^[A-Z\\u00C0-\\u017F\\s]+$";
		Pattern patternFirstName = Pattern.compile(regexFirstName, Pattern.CASE_INSENSITIVE);
		Matcher matcherFirstName = patternFirstName.matcher(FIRST_NAME);

		if(!matcherFirstName.matches()) {
			validationError.addFieldError("firstName", "First name contains invalid characters");
		}

		if(StringUtils.isBlank(FIRST_NAME))
			validationError.addFieldError("firstName", "First name cannot be empty.");
		else {
			if(FIRST_NAME.length() < 3 || FIRST_NAME.length() > 40)
				validationError.addFieldError("firstName", "First name must have between 3 and 40 characters");
		}
		
		
		//LastName
		final String LAST_NAME = user.getLastName();

		String regexLastName = "^[A-Z\\u00C0-\\u017F\\s-]+$";
		Pattern patternLastName = Pattern.compile(regexLastName, Pattern.CASE_INSENSITIVE);
		Matcher matcherLastName = patternLastName.matcher(LAST_NAME);

		if(!matcherLastName.matches()) {
			validationError.addFieldError("firstName", "Last name contains invalid characters");
		}
		
		if(StringUtils.isBlank(LAST_NAME))
			validationError.addFieldError("lastName", "Last name cannot be empty.");
		else {
			if(LAST_NAME.length() < 3 || LAST_NAME.length() > 40)
				validationError.addFieldError("lastName", "Last name must have between 3 and 40 characters");
		}
		
		return validationError;
	}

}
