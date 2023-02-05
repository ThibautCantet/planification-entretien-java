package com.soat.planification_entretien.candidat.command.domain_service;

import com.soat.planification_entretien.common.domain.Event;
import com.soat.planification_entretien.candidat.command.domain.Candidat;
import com.soat.planification_entretien.candidat.command.domain.CandidatCrée;
import com.soat.planification_entretien.candidat.command.domain.CandidatNonCrée;
import com.soat.planification_entretien.common.domain_service.Result;
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
