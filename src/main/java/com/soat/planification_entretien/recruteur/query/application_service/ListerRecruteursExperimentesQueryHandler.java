package com.soat.planification_entretien.recruteur.query.application_service;

import java.util.List;

import com.soat.planification_entretien.recruteur.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.domain.port.repository.RecruteurRepository;
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
