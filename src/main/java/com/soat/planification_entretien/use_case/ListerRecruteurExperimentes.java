package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteurExperimentes {

    private final RecruteurPort recruteurPort;

    public ListerRecruteurExperimentes(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public List<Recruteur> execute() {
        return recruteurPort.findExperimentes();

    }
}
