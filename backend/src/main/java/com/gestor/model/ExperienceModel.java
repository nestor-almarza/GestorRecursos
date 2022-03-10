package com.gestor.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gestor.base.Base;


@Entity
@Table(name="EXPERIENCE")
public class ExperienceModel extends Base {

	@Column( length = 50)
	private String title;
	@Column( length = 300)
	private String description;
	@Column( length = 50)
	private String company;
	@Column
	private LocalDate startDate;
	@Column
	private LocalDate endDate;
	@Column
	private Boolean currentlyWorking = false;
	@Column
	private Long duration;
	
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private CandidateModel candidate;

	
	
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
	public Boolean getCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(Boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}
	public CandidateModel getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidateModel candidate) {
		this.candidate = candidate;
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
		
}
