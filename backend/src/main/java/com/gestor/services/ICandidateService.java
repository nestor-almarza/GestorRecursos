package com.gestor.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gestor.domains.Candidate;
import com.gestor.domains.filters.CandidateFilter;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.exceptions.CandidateServiceException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICandidateService {
	
	public CandidatePresenter createCandidate(Candidate candidate );

	public CandidatePresenter getCandidateById(String candidateId); 
	
	public List<CandidatePresenter> getAllCandidate(  );
	
	public void deleteCandidate(String candidateId);

	Page<CandidatePresenter> getCandidatePage(CandidateFilter candidateFilter, Pageable pageable);
	
	public CandidatePresenter updateCandidate(String id, Candidate candidate);

	void deleteListCandidate(List<String> listCandidateId) throws CandidateServiceException;
	
	public void exportPdfCandidateMint(HttpServletResponse response, String candidateId);

	public void exportPdfCandidateManjaro(HttpServletResponse response, String candidateId);
}
