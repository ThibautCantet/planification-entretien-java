package com.soat.planification_entretien.candidat.domain.model;

public record Langage(String nom) {
    public Langage {
        if (nom.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
