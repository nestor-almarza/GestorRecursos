package com.gestor.utils.converter;

import com.gestor.domains.Experience;
import com.gestor.domains.presenter.ExperiencePresenter;
import com.gestor.model.ExperienceModel;

public interface IExperienceConverter {

	public ExperienceModel convert(Experience experience);
	
	public ExperiencePresenter convert(ExperienceModel expModel);
	
	
}
