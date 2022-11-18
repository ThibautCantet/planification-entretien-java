package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private final RecruteurPort candidatPort;

    public CreerRecruteur(RecruteurPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public Integer execute(Recruteur candidat) {
        return candidatPort.save(candidat);
    }

}
