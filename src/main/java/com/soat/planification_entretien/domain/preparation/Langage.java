package com.soat.planification_entretien.domain.preparation;

public record Langage(String value) {
    public Langage {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Le langage ne peut pas être vide");
        }
    }
}
