package com.soat.planification_entretien.entretien.use_case;

import com.soat.planification_entretien.entretien.domain.aggregate.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnulerEntretien {
    private final EntretienRepository entretienRepository;

    public AnnulerEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public boolean execute(int entretienId) {
        var entretien = entretienRepository.findById(entretienId);
        if (entretien.annuler()) {
            entretienRepository.save(entretien);
            return true;
        }
        return false;
    }

}
