package com.gestor.utils.validators;

import com.gestor.domains.Candidate;
import com.gestor.utils.validators.errors.ValidationError;

public interface ICandidateValidator {
	
	public ValidationError validateCandidate(Candidate candidate);
}
