package com.gestor.utils.converter;

import com.gestor.domains.Candidate;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.model.CandidateModel;

public interface ICandidateConverter {
	
	public CandidateModel convert(Candidate candidate);
	
	public CandidatePresenter convert(CandidateModel candidateModel);
	

}
