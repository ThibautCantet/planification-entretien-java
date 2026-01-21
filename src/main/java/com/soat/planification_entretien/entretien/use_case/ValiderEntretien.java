package com.soat.planification_entretien.entretien.use_case;

import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ValiderEntretien {
    private final EntretienRepository entretienRepository;

    public ValiderEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public boolean execute(int entretienId) {
        var entretien = entretienRepository.findById(entretienId);
        if (entretien.valider()) {
            entretienRepository.save(entretien);
            return true;
        }
        return false;
    }

}
