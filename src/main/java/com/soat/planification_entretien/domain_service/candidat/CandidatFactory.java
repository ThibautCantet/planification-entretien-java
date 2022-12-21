package com.soat.planification_entretien.domain_service.candidat;

import com.soat.planification_entretien.domain.Event;
import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatCrée;
import com.soat.planification_entretien.domain.candidat.CandidatNonCrée;
import org.springframework.stereotype.Service;

@Service
public class CandidatFactory {

    public Result<Event, Candidat> create(Integer candidatId, String language, String email, String experienceEnAnnees) {
        Event e;
        try {
            var candidat = new Candidat(candidatId, language, email, Integer.parseInt(experienceEnAnnees));
            e = new CandidatCrée(candidatId);

            return new Result<>(e, candidat);
        } catch (IllegalArgumentException ex) {
            e = new CandidatNonCrée();
            return new Result(e);
        }
    }
}
