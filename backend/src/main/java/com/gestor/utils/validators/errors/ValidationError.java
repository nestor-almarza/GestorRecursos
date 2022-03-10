package com.gestor.utils.validators.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<FieldError> errors = new ArrayList<>();

	public void addFieldError(FieldError fieldError) {
		errors.add(fieldError);
	}
	
	public void addFieldError(String field, String error) {
		errors.add(new FieldError(field, error));
	}
	
	public void addAllErrors(List<FieldError> errors) {
		this.errors.addAll(errors);
	}
	
	public List<FieldError> getErrors() {
		return errors;
	}
	
	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}
	
	
}
