package com.soat.planification_entretien.domain.entretien.command.entity;

import java.util.List;

public record Recruteur(int id, String language, String email, Integer experienceInYears, List<RendezVous> rendezVous) {
    boolean peutTester(Candidat candidat) {
        return language.equals(candidat.language())
                && experienceInYears > candidat.experienceInYears();
    }
}
