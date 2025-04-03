package com.soat.planification_entretien.entretien.use_case;

import com.soat.planification_entretien.entretien.domain.CandidatRepository;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienId;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ValiderEntretien {

    private EntretienRepository entretienRepository;

    public ValiderEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public void execute(EntretienId entretienId) {
        Entretien entretien = entretienRepository.findById(entretienId);
        entretien.valider();
        entretienRepository.save(entretien);
    }
}
