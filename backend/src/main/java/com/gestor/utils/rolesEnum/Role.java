package com.gestor.utils.rolesEnum;

public enum Role {
	ROLE_CANDIDATE,
	ROLE_EMPLOYER;

	public Boolean hasSameUserPolicy(){

		if(this == Role.ROLE_CANDIDATE)
			return true;

		return false;
	}
}
