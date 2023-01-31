package com.soat.planification_entretien.domain.recruteur;

public record Experience(Integer annee) {
    private static final int MINIMUM_XP_REQUISE = 3;

    public Experience {
        if (annee < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
    }
}
