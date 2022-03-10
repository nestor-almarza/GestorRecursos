package com.antartyca.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.gestor.model.CandidateModel;
import com.gestor.model.ExperienceModel;
import com.gestor.repository.ICandidateRepository;
import com.gestor.repository.IExperienceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ICandidateRepositoryTest {

    @Autowired
    ICandidateRepository candidateRepository ;

    @Autowired
    IExperienceRepository experienceRepository;

    CandidateModel candidate = new CandidateModel();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        candidateRepository.deleteAll();
    }

    @Test
    void createCandidateWithExperiences() {
        //given
        ExperienceModel experience = new ExperienceModel();

        experience.setTitle("Jornalero");

        Set<ExperienceModel> experienceSet = new HashSet<>();
        experienceSet.add(experience);


        candidate.setFirstName("Prueba nombre");
        candidate.setLastName("Prueba apellido");
        candidate.setExperiences(experienceSet);

        //when
        CandidateModel savedCandidate = candidateRepository.save(candidate);
        CandidateModel result = candidateRepository.findById(savedCandidate.getId()).get();


        //then
        assertThat(result.getId()).isNotBlank();

        assertThat( candidate.getExperiences() ).isNotNull();

        result.getExperiences().forEach(
                xp -> assertThat(xp.getId()).isNotBlank()
        );

        assertThat( candidate.getExperiences().size() ).isEqualTo( result.getExperiences().size() );

        assertThat( candidate.getExperiences() ).isEqualTo( result.getExperiences() );
    }

    @Test
    void deleteCandidateAndExperiences(){
        //given
        ExperienceModel experience = new ExperienceModel();

        experience.setTitle("Jornalero");

        Set<ExperienceModel> experienceSet = new HashSet<>();
        experienceSet.add(experience);

        candidate.setFirstName("Prueba nombre");
        candidate.setLastName("Prueba apellido");
        candidate.setExperiences(experienceSet);
        //when
        CandidateModel savedCandidate = candidateRepository.save(candidate);
        Set<ExperienceModel> savedExperience = savedCandidate.getExperiences();
        candidateRepository.deleteById(savedCandidate.getId());

        //then
        assertThat(candidateRepository.findById(savedCandidate.getId())).isEqualTo(Optional.empty());
        savedExperience.forEach( xp ->
              assertThat(experienceRepository.findById(xp.getId())).isEqualTo(Optional.empty()));
    }

    @Test
    void updateCandidate(){
        //given
        ExperienceModel experience = new ExperienceModel();
        LocalDate date = LocalDate.of(2017, 1, 23);
        Set<ExperienceModel> experienceSet = new HashSet<>();

        experience.setTitle("experience1");

        experienceSet.add(experience);

        candidate.setFirstName("name1");
        candidate.setLastName("surname1");
        candidate.setBirthDate(date);
        candidate.setExperiences(experienceSet);

        //when
        CandidateModel savedCandidate = candidateRepository.save(candidate);

        LocalDate date2 = LocalDate.of(2000, 1, 23);
        savedCandidate.setFirstName("name2");
        savedCandidate.setLastName("name2");
        savedCandidate.setBirthDate(date2);

        CandidateModel modifiedCandidate = candidateRepository.save(savedCandidate);

        //then
        assertAll("modifiedCandidate",
                () -> assertEquals("name2", modifiedCandidate.getFirstName()),
                () -> assertEquals("name2", modifiedCandidate.getLastName()),
                () -> assertEquals("2000-01-23", String.valueOf(modifiedCandidate.getBirthDate())),
                () -> modifiedCandidate.getExperiences().forEach(
                        xp -> assertEquals("experience1",xp.getTitle() )
                )
        );
    }




}