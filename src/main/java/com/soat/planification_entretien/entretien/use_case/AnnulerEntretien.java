package com.soat.planification_entretien.entretien.use_case;

import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienId;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.profil.domain.ProspectRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnulerEntretien {

    private final EntretienRepository repository;

    public AnnulerEntretien(EntretienRepository repository) {
        this.repository = repository;
    }

    public void execute(EntretienId entretienId) {
        Entretien entretien = repository.findById(entretienId);
        entretien.annuler();
        repository.save(entretien);
    }
}
