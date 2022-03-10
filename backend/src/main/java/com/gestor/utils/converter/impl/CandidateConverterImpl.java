package com.gestor.utils.converter.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.gestor.domains.Candidate;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.domains.presenter.ExperiencePresenter;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.model.CandidateModel;
import com.gestor.model.ExperienceModel;
import com.gestor.model.UserModel;
import com.gestor.utils.converter.ICandidateConverter;
import com.gestor.utils.converter.IExperienceConverter;
import com.gestor.utils.converter.IUserConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "candidateConverterImpl")
public class CandidateConverterImpl implements ICandidateConverter{
	
	@Autowired
	IExperienceConverter experienceConverterImpl;
	
	@Autowired
	IUserConverter userConverterImpl;
	
	@Override
	public CandidateModel convert(Candidate candidate) {
		
		CandidateModel canModel = new CandidateModel();
		
		Set<ExperienceModel> setExpModel = new HashSet<>();
		
		if (candidate.getExperiences() != null && !candidate.getExperiences().isEmpty()) {
		
		setExpModel = candidate.getExperiences()
				.stream()
				.map(  e  ->  experienceConverterImpl.convert(e) )
				.collect(Collectors.toSet());
		}
		
		canModel.setId(candidate.getId());
		canModel.setExperiences(setExpModel);
		
		canModel.setBirthDate(candidate.getBirthDate());
		canModel.setFirstName(candidate.getFirstName());
		canModel.setLastName(candidate.getLastName());
		canModel.setObservation(candidate.getObservation());
		
		if(candidate.getUser() != null)
			canModel.setUser(new UserModel(candidate.getUser().getId()));
		
		return canModel;
	}

	@Override
	public CandidatePresenter convert(CandidateModel candidateModel) {
		
		CandidatePresenter canPresenter = new CandidatePresenter();
		
		Set<ExperiencePresenter> setExpPre = new HashSet<>();
		
		if(  candidateModel.getExperiences() != null && !candidateModel.getExperiences().isEmpty()  ) {
			
			setExpPre =  candidateModel.getExperiences()
					.stream()
					.map(  e ->  experienceConverterImpl.convert(e) )
					.collect(Collectors.toSet());
		}
		
		canPresenter.setExperiences(setExpPre);
		 
		canPresenter.setId(candidateModel.getId());
		canPresenter.setCreateAt(candidateModel.getCreateAt());
		canPresenter.setUpdatedAt(candidateModel.getUpdatedAt());
		canPresenter.setFirstName(candidateModel.getFirstName());
		canPresenter.setLastName(candidateModel.getLastName());
		canPresenter.setBirthDate(candidateModel.getBirthDate());
		canPresenter.setObservation(candidateModel.getObservation());
		
		if(candidateModel.getUser() != null) {
			if(candidateModel.getUser().getFirstName() != null && candidateModel.getUser().getLastName() != null) {
				String fullName = candidateModel.getUser().getFirstName() + " " + candidateModel.getUser().getLastName();
				
				canPresenter.setUser(new UserPresenter(candidateModel.getUser().getId(), fullName));
			}
			else {
				canPresenter.setUser(new UserPresenter(candidateModel.getUser().getId()));
			}
			
		}
			
		
		return canPresenter;
	}



}












