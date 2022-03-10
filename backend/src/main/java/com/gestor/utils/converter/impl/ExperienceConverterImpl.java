package com.gestor.utils.converter.impl;

import java.time.LocalDate;
import java.time.Period;

import com.gestor.domains.Experience;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.domains.presenter.ExperiencePresenter;
import com.gestor.model.CandidateModel;
import com.gestor.model.ExperienceModel;
import com.gestor.utils.converter.IExperienceConverter;

import org.springframework.stereotype.Service;

@Service(value = "experienceConverterImpl")
public class ExperienceConverterImpl implements IExperienceConverter{

	@Override
	public ExperienceModel convert(Experience experience) {
		
		ExperienceModel experienceModel = new ExperienceModel();

		experienceModel.setId				(experience.getId());
		experienceModel.setTitle			(experience.getTitle());
		experienceModel.setDescription		(experience.getDescription());
		experienceModel.setCompany			(experience.getCompany());
		experienceModel.setStartDate		(experience.getStartDate());
		experienceModel.setCurrentlyWorking	(experience.getCurrentlyWorking());

		experienceModel.setCandidate(
				new CandidateModel( experience.getCandidate().getId() )
		);

		if ( experience.getEndDate() != null )
			experienceModel.setEndDate( experience.getEndDate() );



		experienceModel.setDuration(
				Period.between(
						experience.getStartDate(),
						// checks whether it has endDate or it's not currently working
						( null != experience.getEndDate() || !experience.getCurrentlyWorking()
								// if true
								? experience.getEndDate()
								// if false
								: LocalDate.now() )
						)
						.toTotalMonths()
				);

		return experienceModel;
	}

	@Override
	public ExperiencePresenter convert(ExperienceModel experienceModel) {
		
		ExperiencePresenter experiencePresenter = new ExperiencePresenter();
		
		experiencePresenter.setId				(experienceModel.getId());
		experiencePresenter.setCreateAt			(experienceModel.getCreateAt());
		experiencePresenter.setUpdatedAt		(experienceModel.getUpdatedAt());
		experiencePresenter.setTitle			(experienceModel.getTitle());
		experiencePresenter.setDescription		(experienceModel.getDescription());
		experiencePresenter.setCompany			(experienceModel.getCompany());
		experiencePresenter.setStartDate		(experienceModel.getStartDate());
		experiencePresenter.setEndDate			(experienceModel.getEndDate());
		experiencePresenter.setCurrentlyWorking	(experienceModel.isCurrentlyWorking());
		experiencePresenter.setDuration			(experienceModel.getDuration());
		
		experiencePresenter.setCandidate(
				new CandidatePresenter(experienceModel.getCandidate().getId() )
		);
		
		return experiencePresenter;
	}

}















