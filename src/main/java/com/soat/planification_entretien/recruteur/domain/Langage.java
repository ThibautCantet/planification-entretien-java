package com.soat.planification_entretien.recruteur.domain;

public record Langage(String nom) {
    public Langage {
        if (nom.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
