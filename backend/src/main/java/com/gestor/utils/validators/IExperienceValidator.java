package com.gestor.utils.validators;

import java.util.Set;

import com.gestor.domains.Experience;
import com.gestor.utils.validators.errors.ValidationError;

public interface IExperienceValidator {
	public ValidationError validate(Experience experience);

	ValidationError validateAll(Set<Experience> set);
}
