package com.soat.planification_entretien.application.use_case;

import com.soat.planification_entretien.application.use_case.output_port.CandidatPort;
import com.soat.planification_entretien.domain.model.Candidat;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatPort candidatPort;

    public CreerCandidat(CandidatPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            Candidat candidat = new Candidat(language, email, Integer.parseInt(experienceEnAnnees));
            return candidatPort.save(candidat);
        } catch (Exception e) {
            return null;
        }
    }

}
