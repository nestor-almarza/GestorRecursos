package com.gestor.utils.sanitizer.impl;

import com.gestor.domains.Candidate;
import com.gestor.utils.sanitizer.ICandidateSanitizer;
import com.gestor.utils.sanitizer.IExperienceSanitizer;
import com.gestor.utils.sanitizer.customFormatter.CustomFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("candidateSanitizerImpl")
public class CandidateSanitizerImpl implements ICandidateSanitizer {

    @Autowired
    IExperienceSanitizer experienceSanitizer;

    @Override
    public Candidate sanitize(Candidate candidate) {

        candidate.setFirstName( CustomFormatter.capitalizeNames(candidate.getFirstName()) );

        candidate.setLastName( CustomFormatter.capitalizeNames(candidate.getLastName()) );

        candidate.setObservation(candidate.getObservation().trim());

        candidate.setExperiences(
                experienceSanitizer.sanitize(candidate.getExperiences())
        );

        return candidate;
    }

}
