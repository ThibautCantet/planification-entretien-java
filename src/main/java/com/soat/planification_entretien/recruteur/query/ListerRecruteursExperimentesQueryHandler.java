package com.soat.planification_entretien.recruteur.query;

import java.util.List;

import com.soat.planification_entretien.recruteur.command.domain.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentesQueryHandler {
    private final RecruteurRepository recruteurRepository;

    public ListerRecruteursExperimentesQueryHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public List<Recruteur> handle() {
        return recruteurRepository.find10AnsExperience();
    }
}
