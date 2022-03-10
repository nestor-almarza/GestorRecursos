package com.gestor.repository;

import com.gestor.model.ExperienceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienceRepository extends JpaRepository<ExperienceModel, String> {

}
