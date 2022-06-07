package com.soat.planification_entretien.domain.entretien.command.entity;

public record Recruteur(int id, String language, String email, Integer experienceInYears) {
    boolean peutTester(Candidat candidat) {
        return language.equals(candidat.language())
                && experienceInYears > candidat.experienceInYears();
    }
}
