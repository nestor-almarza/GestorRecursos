package com.gestor.utils.validators.impl;

import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gestor.domains.Experience;
import com.gestor.utils.validators.IExperienceValidator;
import com.gestor.utils.validators.errors.ValidationError;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("experienceValidatorImpl")
public class ExperienceValidatorImpl implements IExperienceValidator {

	@Override
	public ValidationError validateAll(Set<Experience> experiences) {
		
		
		for ( Experience experience : experiences ) {
			ValidationError validationError = validate(experience);
			
			if(validationError.getErrors().size() != 0) {
				return validationError;
			}
		}
		
		return new ValidationError();
	}
	
	@Override
	public ValidationError validate(Experience experience) {
		
		ValidationError validationError = new ValidationError();
		
		if(
				StringUtils.isBlank(experience.getTitle()) 
				|| StringUtils.isBlank(experience.getDescription()) 
				|| StringUtils.isBlank(experience.getCompany())
				|| ObjectUtils.isEmpty(experience.getStartDate())
				) {
			validationError.addFieldError("experience", "There are empty data fields.");
			
			return validationError;
			
		}

		String strCompany = experience.getCompany();
		String regexCompany = "^[A-Z0-9\\u00C0-\\u017F\\s-]+$";
		Pattern patternCompany = Pattern.compile(regexCompany, Pattern.CASE_INSENSITIVE);
		Matcher matcherCompany = patternCompany.matcher(strCompany);

		if(!matcherCompany.matches())
			validationError.addFieldError("experience - company", "Company contains invalid characters");

		String srtTitle = experience.getTitle();
		String regexTitle = "^[A-Z0-9\\u00C0-\\u017F\\u00AA\\u00BA\\s-]+$";
		Pattern patternTitle = Pattern.compile(regexTitle, Pattern.CASE_INSENSITIVE);
		Matcher matcherTitle = patternTitle.matcher(srtTitle);

		if(!matcherTitle.matches())
			validationError.addFieldError("experience - title", "Title contains invalid characters");
		
		if(experience.getTitle().length() < 3 || experience.getTitle().length() > 50)
			validationError.addFieldError("experience - title", "Title must have between 3 and 50 characters");
		
		if(experience.getCompany().length() < 3 || experience.getCompany().length() > 50)
			validationError.addFieldError("experience - company", "Company must have between 3 and 50 characters");
		
		if(experience.getDescription().length() < 3 || experience.getDescription().length() > 300)
			validationError.addFieldError("experience - description", "Description must have between 3 and 300 characters");
		
		if(!validatePeriod(experience.getStartDate(), experience.getEndDate(), experience.getCurrentlyWorking()) )
			validationError.addFieldError("experience - dates", "StartDate, endDate and/or currentlyWorking are not compatible");

		return validationError;
	}
	
	private Boolean validatePeriod(LocalDate startDate, LocalDate endDate, Boolean currentlyWorking) {
		
		if(ObjectUtils.isEmpty(endDate) && !currentlyWorking) 
			return false;
		
			
		if(startDate.isAfter(LocalDate.now()))
			return false;
		
		
		if(!ObjectUtils.isEmpty(endDate) && startDate.isAfter(endDate))
			return false;
		
		return true;
	}


}
