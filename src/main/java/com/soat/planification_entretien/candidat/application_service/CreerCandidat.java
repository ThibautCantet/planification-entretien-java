package com.soat.planification_entretien.candidat.application_service;

import java.util.List;

import com.soat.planification_entretien.common.domain.Event;
import com.soat.planification_entretien.candidat.domain.Candidat;
import com.soat.planification_entretien.candidat.domain.CandidatCrée;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.candidat.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.domain_service.Result;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;

    public CreerCandidat(CandidatRepository candidatRepository, CandidatFactory candidatFactory) {
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
    }

    public List<Event> execute(String language, String email, String experienceEnAnnees) {
        var candidatId = candidatRepository.next();
        Result<Event, Candidat> eventCandidatResult = candidatFactory.create(candidatId, language, email, experienceEnAnnees);

        if (eventCandidatResult.event() instanceof CandidatCrée) {
            candidatRepository.save(eventCandidatResult.value());
        }

        return List.of(eventCandidatResult.event());
    }


}
