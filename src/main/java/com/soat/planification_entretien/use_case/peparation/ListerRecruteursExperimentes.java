package com.soat.planification_entretien.use_case.peparation;

import java.util.List;

import com.soat.planification_entretien.domain.preparation.Recruteur;
import com.soat.planification_entretien.domain.preparation.RecruteurRepository;
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
