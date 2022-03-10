package com.gestor.domains.presenter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gestor.base.Base;

public class CandidatePresenter extends Base implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private Set<ExperiencePresenter> experiences  = new HashSet<>(); ;
	private String observation;
	private UserPresenter user;
	
	public CandidatePresenter() {
		super();
	}
	
	public CandidatePresenter(String candidateId) {
         super( candidateId);
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public Set<ExperiencePresenter> getExperiences() {
		return experiences;
	}
	public void setExperiences(Set<ExperiencePresenter> experiences ) {
		this.experiences = experiences;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserPresenter getUser() {
		return user;
	}

	public void setUser(UserPresenter user) {
		this.user = user;
	}
	
	
	
	

}
