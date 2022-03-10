package com.gestor.domains.presenter;

import java.io.Serializable;
import java.time.LocalDate;

import com.gestor.base.Base;

public class ExperiencePresenter  extends Base implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	private String company;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean currentlyWorking;
	// duration in months //
	private Long duration;
	private CandidatePresenter candidate ;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public CandidatePresenter getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidatePresenter candidate) {
		this.candidate = candidate;
	}


}
