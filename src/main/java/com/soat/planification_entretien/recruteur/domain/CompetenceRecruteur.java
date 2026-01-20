package com.soat.planification_entretien.recruteur.domain;

public record CompetenceRecruteur(String langage, int experienceEnAnnees) {
    private static final int MINIMUM_XP_REQUISE = 3;

    static CompetenceRecruteur of(String langage, int experienceEnAnnees) {
        if (langage.isBlank() || experienceEnAnnees < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
        return new CompetenceRecruteur(langage, experienceEnAnnees);
    }
}
