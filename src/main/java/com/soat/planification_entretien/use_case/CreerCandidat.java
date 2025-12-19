package com.soat.planification_entretien.use_case;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatPort;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {
    private final CandidatPort candidatPort;

    public CreerCandidat(CandidatPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public Candidat execute(String language, String email, String experienceEnAnnees) {
        var candidat = Candidat.of(language, email, experienceEnAnnees);

        if (candidat == null) {
            return null;
        }

        return candidatPort.save(candidat);
    }
}
