package com.soat.planification_entretien.candidat.domain;

public record Competence(String langage, int experienceEnAnnees) {
    static Competence of(String langage, int experienceEnAnnees) {
        if (langage.isBlank() || experienceEnAnnees < 0) {
            throw new IllegalArgumentException();
        }
        return new Competence(langage, experienceEnAnnees);
    }
}
