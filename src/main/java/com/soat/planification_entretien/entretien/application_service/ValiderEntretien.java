package com.soat.planification_entretien.entretien.application_service;

import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
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
