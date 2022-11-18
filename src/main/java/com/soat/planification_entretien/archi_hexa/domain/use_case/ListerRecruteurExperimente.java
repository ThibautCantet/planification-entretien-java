package com.soat.planification_entretien.archi_hexa.domain.use_case;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.domain.entity.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteurExperimente {

    private final RecruteurPort recruteurPort;

    public ListerRecruteurExperimente(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public List<RecruteurExperimente> execute() {
        return recruteurPort.findAll()
                .stream()
                .filter(recruteur -> recruteur.experienceInYears() >= 10)
                .map(r -> new RecruteurExperimente(r.email(), r.language(), r.experienceInYears()))
                .toList();
    }
}
