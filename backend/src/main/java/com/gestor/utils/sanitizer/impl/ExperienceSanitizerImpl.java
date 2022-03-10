package com.gestor.utils.sanitizer.impl;

import org.springframework.stereotype.Service;

import java.util.Set;

import com.gestor.domains.Experience;
import com.gestor.utils.sanitizer.IExperienceSanitizer;
import com.gestor.utils.sanitizer.customFormatter.CustomFormatter;

@Service("experienceSanitizerImpl")
public class ExperienceSanitizerImpl implements IExperienceSanitizer {
    @Override
    public Set<Experience> sanitize(Set<Experience> experiences) {

        for ( Experience experience : experiences) {
            experience.setCompany( CustomFormatter.capitilizeText(experience.getCompany()) );
            experience.setDescription(experience.getDescription().trim());
            experience.setTitle( CustomFormatter.capitilizeText( experience.getTitle() ));
        }
        return experiences;
    }
}
