package com.gestor.services.impl;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import com.gestor.domains.Candidate;
import com.gestor.domains.filters.CandidateFilter;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.exceptions.CandidateServiceException;
import com.gestor.model.CandidateModel;
import com.gestor.repository.ICandidateRepository;
import com.gestor.services.ICandidateService;
import com.gestor.utils.converter.ICandidateConverter;
import com.gestor.utils.pdfExporter.CandidateManjaroExporterPDF;
import com.gestor.utils.pdfExporter.CandidateMintExporterPDF;
import com.lowagie.text.DocumentException;

@Service(value = "candidateServiceImpl")
public class CandidateServiceImpl implements ICandidateService{
	
	@Autowired
	private ICandidateConverter candidateConverterImpl;
	
	@Autowired
	private ICandidateRepository candidateRepository;
	
	public CandidateServiceImpl(){}

	public CandidateServiceImpl(ICandidateRepository candidateRepository) {
	}

	@Override
	public CandidatePresenter createCandidate(Candidate candidate) {
		
		if(  candidate.getExperiences() == null ) {
			
			CandidateModel canModel = candidateConverterImpl.convert(candidate);
			canModel = candidateRepository.save( canModel );
			CandidatePresenter canPresenter = candidateConverterImpl.convert(canModel);
			
			return canPresenter;
		};
		
		CandidateModel canModel = candidateConverterImpl.convert(candidate);
		
		CandidateModel canModelEnd = canModel;
		canModel.getExperiences()
		.forEach( e -> e.setCandidate(canModelEnd) );
		 
		 canModel = candidateRepository.save( canModel );
		
		CandidatePresenter canPresenter = candidateConverterImpl.convert(canModel);
		
		return canPresenter;
	} 

	@Override
	public CandidatePresenter getCandidateById(String candidateId) throws NoSuchElementException{
		
			
		CandidateModel canModel = candidateRepository.findById(candidateId).orElseThrow();
		
		CandidatePresenter canPresenter = candidateConverterImpl.convert(canModel);
		
		return canPresenter;
	}

	@Override
	public List<CandidatePresenter> getAllCandidate() {
		
			List<CandidateModel> listCandidateModel = candidateRepository.findAll();

			List<CandidatePresenter> listCanPresenter = listCandidateModel
					.stream()
					.map(e -> candidateConverterImpl.convert(e) )
					.collect(Collectors.toList());

			return listCanPresenter;
		
	}

	@Override
	public void deleteCandidate(String candidateId) throws EmptyResultDataAccessException {
		
          candidateRepository.deleteById(candidateId);
	}

	@Override
	public Page<CandidatePresenter> getCandidatePage(CandidateFilter candidateFilter, Pageable pageable)
			throws PropertyReferenceException {
		
		//// FILTER CONSTANTS ////
		final String PARTNAME = candidateFilter.getPartname();
		final Date UPDATED_AT_START_RANGE = candidateFilter.getUpdatedAtStartRange();
		final Date UPDATED_AT_END_RANGE = candidateFilter.getUpdatedAtEndRange();
		
		return candidateRepository.findAll(
				(Specification<CandidateModel>) (root, query, criteriaBuilder) -> {
					
					Predicate predicate = criteriaBuilder.conjunction();
					
					if(!StringUtils.isBlank(PARTNAME)) {
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("firstName"), "%"+PARTNAME+"%"));
						predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(root.get("lastName"), "%"+PARTNAME+"%"));
					}

					if( UPDATED_AT_START_RANGE != null ) {
						predicate = criteriaBuilder.and(
								predicate,
								criteriaBuilder.greaterThanOrEqualTo(
										root.get("updatedAt"),
										UPDATED_AT_START_RANGE
								)
						);
					}

					if( UPDATED_AT_END_RANGE != null ) {
						predicate = criteriaBuilder.and(
								predicate,
								criteriaBuilder.lessThanOrEqualTo(
										root.get("updatedAt"),
										UPDATED_AT_END_RANGE
								)
						);
					}
						
					return predicate;
			
		}, pageable)
				.map(candidateModel -> candidateConverterImpl.convert(candidateModel));
	}

	
	
	public CandidatePresenter updateCandidate(String id, Candidate candidate) throws  NoSuchElementException{
		
		if( !candidateRepository.existsById(id)) {
			throw new NoSuchElementException("Such candidate doesn't exist");
		}
		if(StringUtils.isBlank(candidate.getId() )){
		
		candidate.setId(id);
		}
		CandidateModel newModel = candidateConverterImpl.convert(candidate); 
			
       newModel = candidateRepository.save(newModel);
			
		CandidatePresenter candidatePresenter = candidateConverterImpl.convert(newModel);
		
		return candidatePresenter;
	}
	
	
	@Override
	public void deleteListCandidate(List<String> listCandidateId) throws CandidateServiceException {
		
		if(listCandidateId == null || listCandidateId.isEmpty())
			throw new CandidateServiceException("Cannot delete from an empty candidate list.");
		
		List<CandidateModel> listCandidateModel = this.candidateRepository.findAllById(listCandidateId);
		
		if(listCandidateModel.size() != listCandidateId.size())
			throw new CandidateServiceException("Some candidates don't exist.");
	
			this.candidateRepository.deleteAllById(listCandidateId);
	}

	@Override
	public void exportPdfCandidateMint(HttpServletResponse response,String candidateId)
			throws NoSuchElementException {
		
		CandidateModel canModel = candidateRepository.findById(candidateId).orElseThrow();
		
		CandidatePresenter candidatePresenter = candidateConverterImpl.convert(canModel);
		
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + candidatePresenter.getLastName() + "-"
				+ candidatePresenter.getFirstName() + "-" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		CandidateMintExporterPDF exporter =
				new CandidateMintExporterPDF(candidatePresenter, new Color(93, 163, 200));
			try {
				exporter.export(response );
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void exportPdfCandidateManjaro(HttpServletResponse response, String candidateId)
			throws NoSuchElementException{

		CandidateModel canModel = candidateRepository.findById(candidateId).orElseThrow();

		CandidatePresenter candidatePresenter = candidateConverterImpl.convert(canModel);

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + candidatePresenter.getLastName() + "-"
				+ candidatePresenter.getFirstName() + "-" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		CandidateManjaroExporterPDF exporter =
				new CandidateManjaroExporterPDF(candidatePresenter, new Color(48, 48, 48));
		try {
			exporter.export(response );
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

	}
}







