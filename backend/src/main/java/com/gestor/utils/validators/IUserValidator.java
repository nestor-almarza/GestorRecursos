package com.gestor.utils.validators;

import com.gestor.domains.User;
import com.gestor.utils.validators.errors.ValidationError;

public interface IUserValidator {
	
	public ValidationError validateGenerateUser(User user);
	
	public ValidationError validateEditUser(User user);
}
