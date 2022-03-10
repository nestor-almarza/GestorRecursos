package com.gestor.repository;


import com.gestor.model.CandidateModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ICandidateRepository extends JpaRepository<CandidateModel, String>, JpaSpecificationExecutor<CandidateModel> {

	
}
