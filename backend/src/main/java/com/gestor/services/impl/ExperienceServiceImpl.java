package com.gestor.services.impl;

import com.gestor.repository.IExperienceRepository;
import com.gestor.services.IExperienceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("experienceServiceImpl")
public class ExperienceServiceImpl implements IExperienceService {

    @Autowired
    IExperienceRepository experienceRepository;

    @Override
    public void deleteExperienceById(String experienceId) throws EmptyResultDataAccessException {
        experienceRepository.deleteById(experienceId);
    }
}
