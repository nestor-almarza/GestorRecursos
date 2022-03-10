package com.gestor.utils.sanitizer;

import com.gestor.domains.Candidate;

public interface ICandidateSanitizer {
    Candidate sanitize(Candidate candidate);
}
