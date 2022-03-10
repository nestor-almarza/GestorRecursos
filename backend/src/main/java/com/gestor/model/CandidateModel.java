package com.gestor.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gestor.base.Base;


@Entity
@Table(name="CANDIDATE")
public class CandidateModel extends Base{

	@Column( length = 40)
	private String firstName;
	@Column( length = 40)
	private String lastName;
	@Column 
	private LocalDate birthDate;

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
	private Set<ExperienceModel> experiences;
	@Column( length = 300)
	private String observation ;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;
	
	
	public CandidateModel() {
		super();
	}
	public CandidateModel (String candidateId) {
		super(candidateId);
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
	public Set<ExperienceModel> getExperiences() {
		return experiences;
	}
	public void setExperiences(Set<ExperienceModel> experiences) {
		this.experiences = experiences;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	

}
