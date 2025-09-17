package com.soat.planification_entretien.recruteur.domain.model;

public record Langage(String nom) {
    public Langage {
        if (nom.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
