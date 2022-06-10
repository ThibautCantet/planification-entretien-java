package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DisponibilitéConsultantRecruteur {
    public Optional<Recruteur> chercherConsultantRecruteurDisponibleParMois(List<Recruteur> recruteurs, Candidat candidat, LocalDateTime horaire) {
        return recruteurs.stream()
                .filter(recruteur -> recruteur.isCompatible(candidat))
                .filter(recruteur -> recruteur.estDisponible(horaire))
                .findFirst();
    }
}
