package com.soat.planification_entretien.application_service.candidat;

import java.util.List;

import com.soat.planification_entretien.domain.Event;
import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatCrée;
import com.soat.planification_entretien.domain.candidat.CandidatRepository;
import com.soat.planification_entretien.domain_service.candidat.CandidatFactory;
import com.soat.planification_entretien.domain_service.candidat.Result;
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
