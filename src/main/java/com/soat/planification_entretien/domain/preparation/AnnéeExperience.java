package com.soat.planification_entretien.domain.preparation;

public record AnnéeExperience(Integer value) {
    public AnnéeExperience {
        if (value < 0) {
            throw new IllegalArgumentException("L'expérience en années ne peut pas être négative");
        }
    }

    boolean estInférieur(Integer anneesExperience) {
        return value < anneesExperience;
    }
}
