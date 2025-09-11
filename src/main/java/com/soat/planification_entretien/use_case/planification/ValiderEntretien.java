package com.soat.planification_entretien.use_case.planification;

import com.soat.planification_entretien.domain.planification.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ValiderEntretien {
    private final EntretienRepository entretienRepository;

    public ValiderEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public boolean execute(Integer entretienId) {
        try {
            var entretien = entretienRepository.findById(entretienId);
            entretien.valider();
            entretienRepository.save(entretien);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
