package com.soat.planification_entretien.candidat.domain;

public record CompetenceCandidat(String langage, int experienceEnAnnees) {
    static CompetenceCandidat of(String langage, int experienceEnAnnees) {
        if (langage.isBlank() || experienceEnAnnees < 0) {
            throw new IllegalArgumentException();
        }
        return new CompetenceCandidat(langage, experienceEnAnnees);
    }
}
