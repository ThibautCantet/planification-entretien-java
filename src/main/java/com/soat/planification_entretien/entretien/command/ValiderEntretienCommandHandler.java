package com.soat.planification_entretien.entretien.command;

import java.util.Optional;

import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ValiderEntretienCommandHandler {
    private final EntretienRepository entretienRepository;

    public ValiderEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public Optional<Entretien> handle(ValiderEntretienCommand validerEntretienCommand) {
        Optional<Entretien> maybeEntretien = entretienRepository.findById(validerEntretienCommand.entretienId());

        maybeEntretien.ifPresent(entretien -> {
            entretien.valider();
            entretienRepository.save(entretien);
        });
        return maybeEntretien;
    }
}
