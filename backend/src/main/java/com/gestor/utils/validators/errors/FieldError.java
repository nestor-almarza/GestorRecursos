package com.gestor.utils.validators.errors;

import java.io.Serializable;

public class FieldError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String error;
	private String field;
	
	public FieldError() {}
	
	public FieldError(String field, String error) {
		this.error = error;
		this.field = field;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	

}
