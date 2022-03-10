package com.gestor.domains;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.gestor.base.Base;

public class Candidate extends Base implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private Set<Experience> experiences;
	private String observation;
	private User user;
	
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
	
	public Set<Experience> getExperiences() {
		return experiences;
	}
	
	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}
	
	public String getObservation() {
		return observation;
	}
	
	public void setObservation(String observation) {
		this.observation = observation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	

}
