package com.soat.planification_entretien.domain.candidat;

public record Experience(Integer annee) {
    public Experience(Integer annee) {
        if (annee < 0) {
            throw new IllegalArgumentException();
        }
        this.annee = annee;
    }
}
