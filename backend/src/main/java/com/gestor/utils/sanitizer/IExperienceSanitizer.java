package com.gestor.utils.sanitizer;

import java.util.Set;

import com.gestor.domains.Experience;

public interface IExperienceSanitizer {
    Set<Experience> sanitize(Set<Experience> experiences);
}
