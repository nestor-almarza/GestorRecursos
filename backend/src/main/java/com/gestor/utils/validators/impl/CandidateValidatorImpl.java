package com.gestor.utils.validators.impl;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gestor.domains.Candidate;
import com.gestor.utils.validators.ICandidateValidator;
import com.gestor.utils.validators.IExperienceValidator;
import com.gestor.utils.validators.errors.ValidationError;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("candidateValidatorImpl")
public class CandidateValidatorImpl implements ICandidateValidator {

	@Autowired
	IExperienceValidator experienceValidator;
	
	@Override
	public ValidationError validateCandidate(Candidate candidate) {
		
		ValidationError validationError = new ValidationError();
		
		// Candidate cannot be null, but we don't know how the request is composed.
		if(candidate == null) {
			validationError.addFieldError("null", "Candidate doesn't exist.");
			return validationError;
		}


		// First name validations
		final String FIRST_NAME = candidate.getFirstName();
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
		
		
		// Last name validations
		final String LAST_NAME = candidate.getLastName();

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

		// Birthdate validation
		if(!validateDate(candidate.getBirthDate())) {
			validationError.addFieldError("birthdate", "Invalid date for birthdate");
		}

		// Optional - Experience validations
		if( ObjectUtils.isNotEmpty(candidate.getExperiences()) ) {
			ValidationError experienceValidationError = experienceValidator.validateAll(candidate.getExperiences());
			
			validationError.addAllErrors(experienceValidationError.getErrors());
		}
		
		// Optional - Observations validations
		final String OBSERVATION = candidate.getObservation();
		
		if(!StringUtils.isBlank(OBSERVATION)) {
			if(OBSERVATION.length() < 3 || OBSERVATION.length() > 300) 
				validationError.addFieldError("observation", "Observations must have between 3 and 300 characters");
		}
		

		return validationError;
	}
	
	private Boolean validateDate(LocalDate date) {
		if(ObjectUtils.isEmpty(date)) {
			return false;
		}
		
		if(date.isAfter(LocalDate.now())) {
			return false;
		}
		
		return true;
	}
	
}
