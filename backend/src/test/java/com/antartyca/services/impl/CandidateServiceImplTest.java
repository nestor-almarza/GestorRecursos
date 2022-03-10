package com.antartyca.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.model.CandidateModel;
import com.gestor.repository.ICandidateRepository;
import com.gestor.services.impl.CandidateServiceImpl;

@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {
    @Mock
    private ICandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateServiceImpl;


    CandidatePresenter candidatePresenter;

    CandidateModel candidateModel = new CandidateModel();

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

   /*
    @Test
    void canCreateCandidate() {
        //given
        Set<Experience> experience = new HashSet<>();
        Candidate candidateDomain = new Candidate();
        LocalDate date = LocalDate.of(2017, 1, 23);
        candidateDomain.setFirstName("Jaime");
        candidateDomain.setLastName("Vidal");
        candidateDomain.setBirthDate(date);
        candidateDomain.setObservation("Some observations");
        candidateDomain.setExperiences(experience);


        //when
        when(candidateServiceImpl.createCandidate(candidateDomain))
                .thenReturn(candidatePresenter);

        //then
        ArgumentCaptor<CandidateModel> candidateModelArgumentCaptor =
                ArgumentCaptor.forClass(CandidateModel.class);

        verify(candidateRepository).save(candidateModelArgumentCaptor.capture());

        CandidateModel capturedCandidate =
                candidateModelArgumentCaptor.getValue();

        assertThat(capturedCandidate).isEqualTo(candidatePresenter);
    }*/

    @Test
     void getAllCandidate(){
        //when
        when(candidateRepository.findAll()).thenReturn(Arrays.asList(candidateModel));
        // then
        assertNotNull(candidateRepository.findAll());
    }

    @Test
    void throwWhenCantFindCandidateById(){
        //given
        String madeUpId = "44";

        //when
        given(candidateRepository.existsById(madeUpId))
                .willReturn(false)
        ;


    }
}

