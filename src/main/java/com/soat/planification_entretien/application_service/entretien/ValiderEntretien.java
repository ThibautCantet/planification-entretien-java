package com.soat.planification_entretien.application_service.entretien;

import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.Entretien;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.Status;
import org.springframework.stereotype.Service;

@Service
public class ValiderEntretien {
    private final EntretienRepository entretienRepository;

    public ValiderEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public Optional<Entretien> execute(int entretienId) {
        Optional<Entretien> maybeEntretien = entretienRepository.findById(entretienId);

        maybeEntretien.ifPresent(entretien -> {
            entretien.valider();
            entretienRepository.save(entretien);
        });
        return maybeEntretien;
    }
}
