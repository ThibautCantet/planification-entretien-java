package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatPort candidatPort;

    public CreerCandidat(CandidatPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public Integer execute(Candidat candidat) {
        return candidatPort.save(candidat);
    }
}
