package com.gestor.repository;

import com.gestor.base.Base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository extends CrudRepository<Base, String> {

}

