package com.soat.planification_entretien.recruteur.application_service;

import java.util.List;

import com.soat.planification_entretien.recruteur.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentes {
    private final RecruteurRepository recruteurRepository;

    public ListerRecruteursExperimentes(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public List<Recruteur> execute() {
        return recruteurRepository.find10AnsExperience();
    }
}
