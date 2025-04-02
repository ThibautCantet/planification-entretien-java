package com.soat.planification_entretien.profil.use_case;

import java.util.List;

import com.soat.planification_entretien.profil.domain.Recruteur;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
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
