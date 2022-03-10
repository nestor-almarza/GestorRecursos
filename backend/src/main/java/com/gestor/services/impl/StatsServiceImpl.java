package com.gestor.services.impl;

import com.gestor.domains.stats.GeneralStats;
import com.gestor.repository.ICandidateRepository;
import com.gestor.services.IStatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statsServiceImpl")
public class StatsServiceImpl implements IStatsService {

    @Autowired
    ICandidateRepository candidateRepository;

    @Override
    public GeneralStats getGeneralStats() {
        GeneralStats generalStats = new GeneralStats();

        generalStats.setCandidatesCount(
                candidateRepository.count()
        );

        return generalStats;
    }
}
